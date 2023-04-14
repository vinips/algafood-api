package com.vinips.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//Aula 22.3
	//Trocamos a configuração do Security para fazer a authenticação apenas pelo HttpBasic, sem formulário de login nem nada.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
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
	
	//Aula 22.4
	//Criamos uma lista de usuários em memória.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("thiago")
				.password(passwordEncoder().encode("123"))
				.roles("ADMIN")
			.and()
				.withUser("joao")
				.password(passwordEncoder().encode("123"))
				.roles("ADMIN");
	}
	
	//Método para encriptografar senhas
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
