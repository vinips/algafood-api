package com.vinips.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.vinips.algafood.AlgafoodApiApplication;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.model.Estado;
import com.vinips.algafood.domain.repository.CidadeRepository;
import com.vinips.algafood.domain.repository.EstadoRepository;

public class InclusaoCidadeMain {

	public static void main(String[] args) {
		ApplicationContext appContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CidadeRepository cidadeRepository =  appContext.getBean(CidadeRepository.class);
		
		EstadoRepository estadoRepository =  appContext.getBean(EstadoRepository.class);
		
		List<Cidade> lista = cidadeRepository.listar();
		
		for( Cidade r : lista) {
			System.out.println(r.getNome() + " - " + r.getEstado().getNome());
		}
		
		List<Estado> listaEstado = estadoRepository.listar();
		
		Cidade r1 = new Cidade();
		r1.setNome("Japão Delivery");
		r1.setEstado(listaEstado.get(3));
		
		Cidade r2 = new Cidade();
		r2.setNome("Alibaba");
		r2.setEstado(listaEstado.get(4));
		
		r1 = cidadeRepository.salvar(r1);
		r2 = cidadeRepository.salvar(r2);
		
		System.out.printf("%s - %s - %s \n", r1.getId(), r1.getNome(), r1.getEstado().getNome());

		System.out.printf("%s - %s - %s \n", r2.getId(), r2.getNome(), r2.getEstado().getNome());
		
		Cidade r = cidadeRepository.buscar(3L);
		
		System.out.println(r.toString());
		
		Cidade r3 = new Cidade();
		r3.setId(1L);
		r3.setNome("Brasileira");
		r3.setEstado(listaEstado.get(5));
		
		cidadeRepository.salvar(r3);
		
		System.out.println(r3.toString());
		
		
	}

}
