package com.github.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import com.github.douglashiura.picon.ProblemaDeCompilacaoException;
import com.github.douglashiura.picon.linguagem.Parte;
import com.github.douglashiura.picon.linguagem.atribuicao.lista.Estrategia;

public class CampoReferenciaLista extends Campo {

	private Estrategia estrategia;

	public CampoReferenciaLista(String campo, Estrategia estrategia, Parte parte) {
		super(campo, estrategia.getParametros().toString(), parte);
		this.estrategia = estrategia;

	}

	@Override
	Object valor(Class<?> type, Contexto contexto)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			ParseException, NoSuchMethodException, SecurityException, ProblemaDeCompilacaoException {
		return estrategia.valor(type, contexto);
	}
}
