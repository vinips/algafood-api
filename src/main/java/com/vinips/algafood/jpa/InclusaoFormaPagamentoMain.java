package com.vinips.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.vinips.algafood.AlgafoodApiApplication;
import com.vinips.algafood.domain.model.FormaPagamento;
import com.vinips.algafood.domain.repository.FormaPagamentoRepository;

public class InclusaoFormaPagamentoMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		FormaPagamentoRepository formaPagamentoRepository =  appContext.getBean(FormaPagamentoRepository.class);
		
		List<FormaPagamento> lista = formaPagamentoRepository.listar();
		
		for( FormaPagamento r : lista) {
			System.out.println(r.getDescricao() + " - ");
		}
		
		FormaPagamento r1 = new FormaPagamento();
		r1.setDescricao("Permite permissão Parcial");
		

		FormaPagamento r2 = new FormaPagamento();
		r2.setDescricao("Permite Nenhuma permissão");
		
		r1 = formaPagamentoRepository.salvar(r1);
		r2 = formaPagamentoRepository.salvar(r2);
		
		System.out.printf("%s - %s \n", r1.getId(), r1.getDescricao());

		System.out.printf("%s - %s \n", r2.getId(), r2.getDescricao());
		
		
		
		FormaPagamento r = formaPagamentoRepository.buscar(3L);
		
		System.out.println(r.toString());
		
		FormaPagamento r3 = new FormaPagamento();
		r3.setId(1L);
		r3.setDescricao("ADM da Porra toda");
		
		formaPagamentoRepository.salvar(r3);
		
		System.out.println(r3.toString());
		
		FormaPagamento r4 = new FormaPagamento();
		r4.setId(1L);
		
		formaPagamentoRepository.remover(r4);
		
	}

}
