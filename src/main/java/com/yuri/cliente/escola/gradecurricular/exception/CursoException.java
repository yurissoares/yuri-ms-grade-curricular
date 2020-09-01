package com.yuri.cliente.escola.gradecurricular.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CursoException extends RuntimeException {

	private static final long serialVersionUID = -423698696564681950L;

	private final HttpStatus httpStatus;
	
	public CursoException(final String mensagem, final HttpStatus httpStatus) {
		super(mensagem);
		this.httpStatus = httpStatus;
	}
}
