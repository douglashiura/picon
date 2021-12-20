package com.github.douglashiura.picon.junit;

import java.lang.reflect.Field;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import com.github.douglashiura.picon.ProblemaDeCompilacaoException;
import com.github.douglashiura.picon.linguagem.Arquivos;
import com.github.douglashiura.picon.linguagem.Bloco;
import com.github.douglashiura.picon.preguicoso.Contexto;

public class PiconRunner implements TestInstancePostProcessor {

	private static Contexto CONTEXTO;

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
		monteOContexto(testInstance);
	}

	private void monteOContexto(Object object)
			throws ProblemaDeCompilacaoException, IllegalArgumentException, IllegalAccessException {
		CONTEXTO = build();
		Field[] declarado = object.getClass().getDeclaredFields();
		for (Field field : declarado) {
			field.setAccessible(true);
			if (field.get(object) == null) {
				field.set(object, get(Bloco.camelCase(field.getName())));
			}
		}
	}

	public static <T> T get(String field) {
		try {
			return CONTEXTO.get(field);
		} catch (Exception e) {
			return null;
		}
	}

	public static Contexto build() throws ProblemaDeCompilacaoException {
		return new Contexto(Arquivos.getInstance().explodir());
	}

}