package com.vinips.algafood.domain.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.vinips.algafood.domain.event.PedidoConfirmadoEvent;
import com.vinips.algafood.domain.model.Pedido;
import com.vinips.algafood.domain.service.EnvioEmailService;
import com.vinips.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {
	
	@Autowired
	private EnvioEmailService emailService;

	//Esse Método serve para que sempre que um PedidoConfirmadoEvent for lançado, esse método é disparado.
	//@EventListener é para que ele rode o método quando o evento é lançado, independente se a transação terminou ou não.
	//@TransactionalEventListener é para que ele rode o método quando a transação terminar, e não quando o evento é lançado.
	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		
		var destinatarios = new HashSet<String>();
		destinatarios.add(pedido.getCliente().getEmail());
		
		var assunto = pedido.getRestaurante().getNome() + "- Pedido confirmado";
		var corpo = "pedido-confirmado.html";
		
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("pedido", pedido);
		
		Mensagem mensagem = new Mensagem(destinatarios, assunto, corpo, variaveis);	

		emailService.enviar(mensagem);
	}
	
}
