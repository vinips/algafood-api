package com.vinips.algafood.domain.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.vinips.algafood.domain.event.PedidoCanceladoEvent;
import com.vinips.algafood.domain.model.Pedido;
import com.vinips.algafood.domain.service.EnvioEmailService;
import com.vinips.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {
	
	@Autowired
	private EnvioEmailService emailService;

	@TransactionalEventListener
	public void aoCancelarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();
		
		var destinatarios = new HashSet<String>();
		destinatarios.add(pedido.getCliente().getEmail());
		
		var assunto = pedido.getRestaurante().getNome() + "- Pedido Cancelado";
		var corpo = "pedido-cancelado.html";
		
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("pedido", pedido);
		
		Mensagem mensagem = new Mensagem(destinatarios, assunto, corpo, variaveis);	

		emailService.enviar(mensagem);
	}
	
}
