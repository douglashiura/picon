package net.douglashiura.picon;

import net.douglashiura.picon.linguagem.Parte;

public class ProblemaDeCompilacaoException extends Exception {
	private static final long serialVersionUID = 1L;
	private Parte toke;

	public ProblemaDeCompilacaoException(Throwable throwable, Parte t) {
		super(throwable);
		toke = t;
	}

	@Override
	public String getMessage() {
		return "Em linha " + toke.getLinha() + " com \"" + tokes() + "\"";
	}

	public String tokes() {
		String linha = "";
		Parte inicio = toke;
		int maximoDeTokes = 3;
		while (inicio != null && 0 < maximoDeTokes--) {
			linha = inicio.valor() + " " + linha;
			inicio = inicio.getAnterior();
		}
		return linha;
	}

	
}
