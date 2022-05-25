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
	public Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido).orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		
		//Normalmente não precisamos do save, pois a entidade Pedido que recebemos do método buscarOuFalhar
		//Já esta sendo gerenciada pelo JPA, por isso qualquer alteração na entidade será replicada no banco ao final da Transação.
		//Porém quando usamos events, ele só dispara depois que dizemos explicitamente que precisamos do save.
		pedidoRepository.save(pedido);
		
		
		//Jeito antigo, antes era assim, mas depois implementamos o Domain Events.
		//Foi transferido para a classe NotificacaoClientePedidoConfirmadoListener.
//		var destinatarios = new HashSet<String>();
//		destinatarios.add(pedido.getCliente().getEmail());
//		
//		var assunto = pedido.getRestaurante().getNome() + "- Pedido confirmado";
//		var corpo = "pedido-confirmado.html";
//		
//		Map<String, Object> variaveis = new HashMap<>();
//		variaveis.put("pedido", pedido);
//		
//		Mensagem mensagem = new Mensagem(destinatarios, assunto, corpo, variaveis);	
//
//		emailService.enviar(mensagem);
		
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = buscarOuFalhar(codigoPedido);
		pedido.cancelar();
		
		pedidoRepository.save(pedido);
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
