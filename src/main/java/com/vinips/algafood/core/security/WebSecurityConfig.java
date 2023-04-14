package com.vinips.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Aula 22.3
		//Trocamos a configuração do Security para fazer a authenticação apenas pelo HttpBasic, sem formulário de login nem nada.
		http.httpBasic()
			.and()
			.authorizeRequests()
				//libera uma URL para ser acessada sempre, sem autenticação. Bom para endpoints públicos, etc.
				.antMatchers("/v1/cozinhas/**").permitAll()
				//Aqui nós falamos que qualquer Request precisa ser autenticado, tem que ser na ordem de libera - bloqueia caso contrário da erro.
				.anyRequest().authenticated()
			.and()
				.sessionManagement()
					//Config para mudar a sessão para Sem Estado(Stateless), com isso o servidor não salva cookies da sessão.
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				//O Csrf pode ser desabilitado nesse caso pois ele é uma proteção quando usamos cookie. Como desabilitamos os cookies e vamos usar autenticação em cada requisioção, ele pode ser desabilitado.
				.csrf().disable();
	}
	
}
