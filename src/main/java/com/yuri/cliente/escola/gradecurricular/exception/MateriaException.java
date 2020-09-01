package com.yuri.cliente.escola.gradecurricular.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MateriaException extends RuntimeException{

	private static final long serialVersionUID = 3273044084499704220L;

	private final HttpStatus httpStatus;
	
	public MateriaException(final String mensagem, final HttpStatus httpStatus) {
		super(mensagem);
		this.httpStatus = httpStatus;
	}
	
	
}
