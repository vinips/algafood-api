package com.vinips.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.vinips.algafood.AlgafoodApiApplication;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.repository.RestauranteRepository;

public class InclusaoRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		RestauranteRepository restauranteRepository =  appContext.getBean(RestauranteRepository.class);
		
		List<Restaurante> lista = restauranteRepository.listar();
		
		for( Restaurante r : lista) {
			System.out.println(r.getNome() + " - " + r.getTaxaFrete()  + " - " + r.getCozinha().getNome());
		}
		
		Restaurante r1 = new Restaurante();
		r1.setNome("Japão Delivery");
		

		Restaurante r2 = new Restaurante();
		r2.setNome("Alibaba");
		
		r1 = restauranteRepository.salvar(r1);
		r2 = restauranteRepository.salvar(r2);
		
		System.out.printf("%s - %s \n", r1.getId(), r1.getNome(), r1.getTaxaFrete());

		System.out.printf("%s - %s \n", r2.getId(), r2.getNome(), r2.getTaxaFrete());
		
		
		
		Restaurante r = restauranteRepository.buscar(3L);
		
		System.out.println(r.toString());
		
		Restaurante r3 = new Restaurante();
		r3.setId(1L);
		r3.setNome("Brasileira");
		
		restauranteRepository.salvar(r3);
		
		System.out.println(r3.toString());
		
		Restaurante r4 = new Restaurante();
		r4.setId(1L);
		
		restauranteRepository.remover(r4);
		
	}

}
