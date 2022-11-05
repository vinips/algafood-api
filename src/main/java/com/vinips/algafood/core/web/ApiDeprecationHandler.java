package com.vinips.algafood.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//VERSÕES MAIS NOVAS UTILIZAM HandlerInterceptor POIS HandlerInterceptorAdapter FOI DEPRECIADA
//Essa classe serve para interceptar métodos dos controladores
@Component
public class ApiDeprecationHandler extends HandlerInterceptorAdapter {
	
	//Se a uri começar com /v2, adiciona uma msg no cabeçalho. Bom para usar em versões depreciadas da API. Aula 20.18
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getRequestURI().startsWith("/v2/")) {
			//Para desligar a versão da API
			response.setStatus(HttpStatus.GONE.value());
			return false;
			
			//Para informar que esta depreciada
			//response.addHeader("X-AlgaFood-Deprecated", "Essa versão da API foi feita para fins de estudo e não deve ser utilizada. Utilize a versão 1");
		}
		
		return true;
	}

}
