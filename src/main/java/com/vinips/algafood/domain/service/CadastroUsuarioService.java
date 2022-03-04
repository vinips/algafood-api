package com.vinips.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinips.algafood.api.model.input.SenhaInput;
import com.vinips.algafood.domain.exception.EntidadeEmUsoException;
import com.vinips.algafood.domain.exception.SenhaIncorretaException;
import com.vinips.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.vinips.algafood.domain.model.Usuario;
import com.vinips.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removida pois está em uso";
	
	private static final String MSG_SENHA_INCORRETA = "Senha atual informada não coincide com a senha do usuário";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario salvar(Usuario usuario) {
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
