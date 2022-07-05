package com.vinips.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

//Classe para padronizarmos o corpo da mensagem de erro das respostas HTTP no padrão RFC 7807.
@JsonInclude(Include.NON_NULL)
public class Problem {

	@ApiModelProperty(example = "400")
	private Integer status;
	
	@ApiModelProperty(example = "2022-07-05T02:02:25.0389267Z")
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(example = "https://algafood.com.br/recurso-nao-encontrado")
	private String type;
	
	@ApiModelProperty(example = "Recurso não encontrado")
	private String title;
	
	@ApiModelProperty(example = "Forma de Pagamento de código 35 não existe")
	private String detail;
	
	@ApiModelProperty(example = "Forma de Pagamento de código 35 não existe")
	private String userMessage;
	
	@ApiModelProperty(value = "Lista de Objetos ou campos que geraram o erro (opcional)")
	private List<Field> fields;

	public Problem(Integer status, OffsetDateTime timestamp, String type, String title, String detail,
			String userMessage, List<Field> fields) {
		this.status = status;
		this.timestamp = timestamp;
		this.type = type;
		this.title = title;
		this.detail = detail;
		this.userMessage = userMessage;
		this.setFields(fields);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
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

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
