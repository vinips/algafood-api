package com.vinips.algafood.domain.event;

import com.vinips.algafood.domain.model.Pedido;

//É bom usarmos o nome da classe no passado, pois o evento é algo que acabou de acontecer.
public class PedidoCanceladoEvent {

	private Pedido pedido;

	public PedidoCanceladoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	
}
