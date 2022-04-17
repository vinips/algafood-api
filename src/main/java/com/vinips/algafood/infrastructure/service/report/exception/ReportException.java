package com.vinips.algafood.infrastructure.service.report.exception;

public class ReportException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ReportException(String msg) {
		super(msg);
	}
	
	public ReportException(String msg, Throwable causa) {
		super(msg, causa);
	}

}