package com.vinips.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.vinips.algafood.AlgafoodApiApplication;
import com.vinips.algafood.domain.model.Permissao;
import com.vinips.algafood.domain.repository.PermissaoRepository;

public class InclusaoPermissaoMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		PermissaoRepository permissaoRepository =  appContext.getBean(PermissaoRepository.class);
		
		List<Permissao> lista = permissaoRepository.listar();
		
		for( Permissao r : lista) {
			System.out.println(r.getNome() + " - ");
		}
		
		Permissao r1 = new Permissao();
		r1.setNome("Parcial");
		r1.setDescricao("Permite permissão Parcial");
		

		Permissao r2 = new Permissao();
		r2.setNome("Nenhuma");
		r2.setDescricao("Permite Nenhuma permissão");
		
		r1 = permissaoRepository.salvar(r1);
		r2 = permissaoRepository.salvar(r2);
		
		System.out.printf("%s - %s \n", r1.getId(), r1.getNome());

		System.out.printf("%s - %s \n", r2.getId(), r2.getNome());
		
		
		
		Permissao r = permissaoRepository.buscar(3L);
		
		System.out.println(r.toString());
		
		Permissao r3 = new Permissao();
		r3.setId(1L);
		r3.setNome("ADM");
		r3.setDescricao("ADM da Porra toda");
		
		permissaoRepository.salvar(r3);
		
		System.out.println(r3.toString());
		
		Permissao r4 = new Permissao();
		r4.setId(1L);
		
		permissaoRepository.remover(r4.getId());
		
	}

}
