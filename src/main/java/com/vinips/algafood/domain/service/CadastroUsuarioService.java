package com.vinips.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinips.algafood.domain.exception.EntidadeEmUsoException;
import com.vinips.algafood.domain.exception.NegocioException;
import com.vinips.algafood.domain.exception.SenhaIncorretaException;
import com.vinips.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.vinips.algafood.domain.model.Usuario;
import com.vinips.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removida pois está em uso";
	
	private static final String MSG_SENHA_INCORRETA = "Senha atual informada não coincide com a senha do usuário";
	
	private static final String MSG_EMAIL_EXISTENTE = "Já existe um usuário cadastrado com o e-mail '%s'";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format(MSG_EMAIL_EXISTENTE, usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}

	// Aqui ele vai retornar uma Usuario
	// Caso não encontre a usuario, vai lançar uma Exception.
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}

	@Transactional
	public void excluir(Long usuarioId) {
		try {
			usuarioRepository.deleteById(usuarioId);

			usuarioRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
		}

	}

	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuarioAtual = buscarOuFalhar(usuarioId);

		if (!usuarioAtual.compararSenha(senhaAtual)) {
			throw new SenhaIncorretaException(MSG_SENHA_INCORRETA);
		}
		
		usuarioAtual.setSenha(novaSenha);
		
	}

}
