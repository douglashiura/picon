/*Douglas Hiura Longo, 18 de Março de 2018.*/

package com.github.douglashiura.picon.preguicoso;

import com.github.douglashiura.picon.ProblemaDeCompilacaoException;
import com.github.douglashiura.picon.linguagem.Parte;

public class CampoValor extends Campo {

	private String valor;
	private Parte parte;

	public CampoValor(String campo, String valor, Parte parte) {
		super(campo, valor, parte);
		this.valor = valor;
		this.parte = parte;
	}

	@Override
	Object valor(Class<?> type, Contexto contexto) throws ProblemaDeCompilacaoException {
		return Valores.de(type, valor, parte);
	}

}
