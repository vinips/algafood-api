package com.vinips.algafood.api.exceptionhandler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("FieldProblem")
public class Field {
	
	@ApiModelProperty(example = "descricao")
	private String name;
	
	@ApiModelProperty(example = "Descrição da forma de pagamento é obrigatório!")
	private String userMessage;
	
	public Field(String name, String userMessage) {
		super();
		this.name = name;
		this.userMessage = userMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	

}
