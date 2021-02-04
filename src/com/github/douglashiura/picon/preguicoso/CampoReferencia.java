package com.github.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import com.github.douglashiura.picon.ProblemaDeCompilacaoException;
import com.github.douglashiura.picon.linguagem.Parte;

public class CampoReferencia extends Campo {

	private String qualificador;

	public CampoReferencia(String campo, String qualificador, Parte parte) {
		super(campo, qualificador, parte);
		this.qualificador = qualificador;
	}

	@Override
	Object valor(Class<?> type, Contexto contexto)
			throws InstantiationException, IllegalAccessException,  SecurityException,
			IllegalArgumentException, NoSuchMethodException, InvocationTargetException, ParseException, ProblemaDeCompilacaoException {
		return contexto.get(qualificador);
	}

}
