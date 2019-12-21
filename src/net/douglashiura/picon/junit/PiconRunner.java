package net.douglashiura.picon.junit;

<<<<<<< HEAD
import java.lang.reflect.Field;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Arquivos;
import net.douglashiura.picon.linguagem.Bloco;
import net.douglashiura.picon.preguicoso.Contexto;

public class PiconRunner extends BlockJUnit4ClassRunner {

	private static Contexto CONTEXTO;

	public PiconRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		super.runChild(method, notifier);
	}

	@Override
	protected Object createTest() throws Exception {
		Object objectTesting = super.createTest();
		monteOContexto(objectTesting);
		return objectTesting;
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
=======
import java.io.IOException;
import java.lang.reflect.Field;

import net.douglashiura.picon.ProblemaDeCompilacao;
import net.douglashiura.picon.PiconStore;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class PiconRunner extends BlockJUnit4ClassRunner {

	private static PiconStore CONTEXTO;

	public PiconRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		super.runChild(method, notifier);
	}

	@Override
	protected Object createTest() throws Exception {
		Object objectTesting = super.createTest();

		monteOContexto(objectTesting);
		return objectTesting;
	}

	private void monteOContexto(Object object) throws IOException,
			ProblemaDeCompilacao, IllegalArgumentException,
			IllegalAccessException {

		CONTEXTO = build();

		Field[] declarado = object.getClass().getDeclaredFields();
		for (Field field : declarado) {
			field.setAccessible(true);
			if (field.get(object) == null) {
				field.set(object, CONTEXTO.get(camelCase(field.getName())));
			}
		}
	}

	private String camelCase(String name) {
		String camelCase = "";
		boolean sinalNumerico = false;
		boolean sinalUpper = false;

		for (Character n : name.toCharArray()) {
			if (n=='_') {
				camelCase += n;
			} else if (Character.isUpperCase(n) && !sinalUpper) {
				camelCase += ":" + n;
				sinalUpper = true;
				sinalNumerico = false;
			} else if (Character.isDigit(n) && !sinalNumerico) {
				camelCase += ":" + n;
				sinalUpper = false;
				sinalNumerico = true;
			} else if (Character.isUpperCase(n) && sinalUpper) {
				camelCase += n;
				sinalNumerico = false;
			} else if (Character.isDigit(n) && sinalNumerico) {
				camelCase += n;
				sinalUpper = false;
			} else {
				camelCase += n;
				if ((!Character.isUpperCase(n) || !Character.isDigit(n))) {
					sinalNumerico = false;
					sinalUpper = false;
				}
			}
		}
		return camelCase.toLowerCase();

	}

	public static <T> T get(String qualificador) {
		return CONTEXTO.get(qualificador);
	}

	public static <T> T get(String qualificador, Class<T> t) {
		return CONTEXTO.get(qualificador);
	}

	public static PiconStore build() throws IOException, ProblemaDeCompilacao {
		return PiconStore.build(FarejadorDeArquivos.getInstance()
				.toString());
	}

>>>>>>> branch 'master' of https://github.com/douglashiura/picon.git
}