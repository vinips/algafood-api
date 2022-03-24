package com.vinips.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinips.algafood.domain.exception.NegocioException;
import com.vinips.algafood.domain.exception.PedidoNaoEncontradoException;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.model.FormaPagamento;
import com.vinips.algafood.domain.model.Pedido;
import com.vinips.algafood.domain.model.Produto;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.model.Usuario;
import com.vinips.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {

	private static final String MSG_FORMA_PAGAMENTO_NAO_ACEITA = "Forma de Pagamento '%s' não é aceita nesse restaurante";

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	// Boa prática anotar os métodos que fazem manipulação de dados com o
	// @Transactional para não ocorrer erros
	@Transactional
	public Pedido salvar(Pedido pedido) {
		validarPedido(pedido);
		validarItensPedido(pedido);

		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();

		return pedidoRepository.save(pedido);
	}

	// Aqui ele vai retornar uma Forma de Pagamento
	// Caso não encontre a Forma de Pagamento, vai lançar uma Exception.
	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}

	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = buscarOuFalhar(pedidoId);
		pedido.confirmar();
	}
	
	@Transactional
	public void entregar(Long pedidoId) {
		Pedido pedido = buscarOuFalhar(pedidoId);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
		Pedido pedido = buscarOuFalhar(pedidoId);
		pedido.cancelar();
	}

	private void validarPedido(Pedido pedido) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());

		if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(
					String.format(MSG_FORMA_PAGAMENTO_NAO_ACEITA, pedido.getFormaPagamento().getDescricao()));
		}

		Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());

		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);
	}

	private void validarItensPedido(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = cadastroProduto.buscarOuFalhar(item.getProduto().getId(),
					pedido.getRestaurante().getId());

			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}

}
