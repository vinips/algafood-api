package com.vinips.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.vinips.algafood.AlgafoodApiApplication;
import com.vinips.algafood.domain.model.Cozinha;
import com.vinips.algafood.domain.repository.CozinhaRepository;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository cozinhaRepository =  appContext.getBean(CozinhaRepository.class);
		
		Cozinha c1 = new Cozinha();
		c1.setNome("Brasileira");
		

		Cozinha c2 = new Cozinha();
		c2.setNome("Japonesa");
		
		c1 = cozinhaRepository.salvar(c1);
		c2 = cozinhaRepository.salvar(c2);
		
		System.out.printf("%s - %s \n", c1.getId(), c1.getNome());

		System.out.printf("%s - %s \n", c2.getId(), c2.getNome());
		
		List<Cozinha> lista = cozinhaRepository.listar();
		
		for( Cozinha c : lista) {
			System.out.println(c.getNome());
		}
		
		Cozinha c = cozinhaRepository.buscar(3L);
		
		System.out.println(c.toString());
		
		Cozinha c3 = new Cozinha();
		c3.setId(1L);
		c3.setNome("Brasileira");
		
		cozinhaRepository.salvar(c3);
		
		System.out.println(c3.toString());
		
		Cozinha c4 = new Cozinha();
		c4.setId(1L);
		
		cozinhaRepository.remover(c4);
		
	}

}
