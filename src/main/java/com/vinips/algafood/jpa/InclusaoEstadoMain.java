package com.vinips.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.vinips.algafood.AlgafoodApiApplication;
import com.vinips.algafood.domain.model.Estado;
import com.vinips.algafood.domain.repository.EstadoRepository;

public class InclusaoEstadoMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		EstadoRepository estadoRepository =  appContext.getBean(EstadoRepository.class);
		
		List<Estado> lista = estadoRepository.listar();
		
		for( Estado r : lista) {
			System.out.println(r.getNome() + " - ");
		}
		
		Estado r1 = new Estado();
		r1.setNome("Japão Delivery");
		

		Estado r2 = new Estado();
		r2.setNome("Alibaba");
		
		r1 = estadoRepository.salvar(r1);
		r2 = estadoRepository.salvar(r2);
		
		System.out.printf("%s - %s \n", r1.getId(), r1.getNome());

		System.out.printf("%s - %s \n", r2.getId(), r2.getNome());
		
		
		
		Estado r = estadoRepository.buscar(3L);
		
		System.out.println(r.toString());
		
		Estado r3 = new Estado();
		r3.setId(1L);
		r3.setNome("Brasileira");
		
		estadoRepository.salvar(r3);
		
		System.out.println(r3.toString());
		
		Estado r4 = new Estado();
		r4.setId(1L);
		
		estadoRepository.remover(r4);
		
	}

}
