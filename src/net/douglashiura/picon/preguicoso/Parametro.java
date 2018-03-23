package net.douglashiura.picon.preguicoso;

import net.douglashiura.picon.ProblemaDeCompilacaoException;

public interface Parametro {

	Object getValor(Contexto contexto) throws ProblemaDeCompilacaoException;

	String getValorDeclarado();

}
