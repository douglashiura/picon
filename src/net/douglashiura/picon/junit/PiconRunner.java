package net.douglashiura.picon.junit;

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
}