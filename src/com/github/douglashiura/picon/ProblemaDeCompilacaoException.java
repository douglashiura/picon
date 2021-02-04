package com.github.douglashiura.picon;

import com.github.douglashiura.picon.linguagem.Parte;

public class ProblemaDeCompilacaoException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final Integer RASTRO_MAXIMO = 3;
	private Parte toke;

	public ProblemaDeCompilacaoException(Throwable throwable, Parte t) {
		super(throwable);
		toke = t;
	}

	@Override
	public String getMessage() {
		return "Em linha " + (null == toke ? "null" : toke.getLinha()) + " com \"" + tokes() + "\"";
	}

	public String tokes() {
		String linha = "";
		Parte inicio = toke;
		Integer atual = RASTRO_MAXIMO;
		while (inicio != null && 0 < atual--) {
			linha = inicio.valor() + " " + linha;
			inicio = inicio.getAnterior();
		}
		return linha;
	}

}
