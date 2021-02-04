package com.github.douglashiura.picon.preguicoso;

import com.github.douglashiura.picon.ProblemaDeCompilacaoException;

public interface Parametro {

	Object getValor(Contexto contexto) throws ProblemaDeCompilacaoException;

	String getValorDeclarado();

}
