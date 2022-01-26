package com.vinips.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//Classe para padronizarmos o corpo da mensagem de erro das respostas HTTP no padrão RFC 7807.
@JsonInclude(Include.NON_NULL)
public class Problem {

	private Integer status;
	private String type;
	private String title;
	private String detail;

	//Propriedades da extensão do problema.
	private String userMessage;
	private LocalDateTime timestamp;

	public Problem(Integer status, String type, String title, String detail, String userMessage,
			LocalDateTime timestamp) {
		this.status = status;
		this.type = type;
		this.title = title;
		this.detail = detail;
		this.userMessage = userMessage;
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
}
