package com.github.douglashiura.picon.preguicoso;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import com.github.douglashiura.picon.ProblemaDeCompilacaoException;
import com.github.douglashiura.picon.linguagem.Parte;

public class Valores {
	final private static Tempo TEMPO = new Tempo();

	public static Object de(Class<?> type, String valor, Parte parte) throws ProblemaDeCompilacaoException {
		if (type.isEnum()) {
			Object[] enums = type.getEnumConstants();
			for (Object umValor : enums) {
				if (umValor.toString().equals(valor)) {
					return umValor;
				}
			}
			throw new ProblemaDeCompilacaoException(null, parte);
		} else if (type.equals(UUID.class)) {
			return UUID.fromString(valor);
		} else if (type.equals(Date.class)) {
			try {
				return TEMPO.de(valor);
			} catch (ParseException e) {
				throw new ProblemaDeCompilacaoException(e, parte);
			}
		} else {
			try {
				Constructor<?> construtor = type.getDeclaredConstructor(String.class);
				return construtor.newInstance(valor);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				throw new ProblemaDeCompilacaoException(e, parte);
			}
		}
	}
}
