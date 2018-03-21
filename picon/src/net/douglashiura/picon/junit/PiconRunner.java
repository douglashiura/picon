package net.douglashiura.picon.junit;

import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import net.douglashiura.picon.ProblemaDeCompilacao;
import net.douglashiura.picon.linguagem.BlockCase;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.ContextoPreguisoso;

public class PiconRunner extends BlockJUnit4ClassRunner {

	private static ContextoPreguisoso CONTEXTO;

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

	private void monteOContexto(Object object) throws IOException, ProblemaDeCompilacao, IllegalArgumentException, IllegalAccessException {
		build();
		Field[] declarado = object.getClass().getDeclaredFields();
		for (Field field : declarado) {
			field.setAccessible(true);
			if (field.get(object) == null) {
				field.set(object, get(BlockCase.camelCase(field.getName())));
			}
		}
	}

	public static <T> T get(String qualificador) {
		try {
			return CONTEXTO.get(qualificador);
		} catch (Exception e) {
			return null;
		}
	}

	public static ContextoPreguisoso build() throws IOException, ProblemaDeCompilacao {
		return new ContextoPreguisoso(new Qualificadores());
	}
}