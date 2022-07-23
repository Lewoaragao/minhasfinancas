package com.dsousa.minhasfinancas.exceptions;

public class ErroAutenticacao extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7942013094824652272L;

	public ErroAutenticacao(String mensagem) {
		super(mensagem);
	}
}
