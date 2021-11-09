package com.vinips.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.vinips.algafood.AlgafoodApiApplication;
import com.vinips.algafood.domain.model.Cozinha;
import com.vinips.algafood.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository cadastroCozinha =  appContext.getBean(CozinhaRepository.class);
		
		List<Cozinha> lista = cadastroCozinha.listar();
		
		for( Cozinha c : lista) {
			System.out.println(c.getNome());
		}
		
	}

}
