package net.douglashiura.picon.junit;

import java.io.IOException;
import java.lang.reflect.Field;

import net.douglashiura.picon.ExceptionCompilacao;
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
			ExceptionCompilacao, IllegalArgumentException,
			IllegalAccessException {

		CONTEXTO = build();

		Field[] declarado = object.getClass().getDeclaredFields();
		for (Field field : declarado) {
			field.setAccessible(true);
			if (field.get(object) == null) {
				field.set(object, CONTEXTO.get(camel(field.getName())));
			}
		}
	}

	private String camel(String name) {
		String piconCamel = "";
		boolean sinalNumerico = false;
		boolean sinalUpper = false;

		for (Character n : name.toCharArray()) {
			if (n=='_') {
				piconCamel += n;
			} else if (Character.isUpperCase(n) && !sinalUpper) {
				piconCamel += ":" + n;
				sinalUpper = true;
				sinalNumerico = false;
			} else if (Character.isDigit(n) && !sinalNumerico) {
				piconCamel += ":" + n;
				sinalUpper = false;
				sinalNumerico = true;
			} else if (Character.isUpperCase(n) && sinalUpper) {
				piconCamel += n;
				sinalNumerico = false;
			} else if (Character.isDigit(n) && sinalNumerico) {
				piconCamel += n;
				sinalUpper = false;
			} else {
				piconCamel += n;
				if ((!Character.isUpperCase(n) || !Character.isDigit(n))) {
					sinalNumerico = false;
					sinalUpper = false;
				}
			}
		}
		return piconCamel.toLowerCase();

	}

	public static <T> T get(String qualificador) {
		return CONTEXTO.get(qualificador);
	}

	public static <T> T get(String qualificador, Class<T> t) {
		return CONTEXTO.get(qualificador);
	}

	public static PiconStore build() throws IOException, ExceptionCompilacao {
		return PiconStore.build(EstrategiaDeEncontrarEMontarONs.getInstance()
				.toString());
	}

}