package com.vinips.algafood.api.exceptionhandler;

public class Field {
	
	private String name;
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
