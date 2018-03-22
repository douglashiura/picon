package net.douglashiura.picon.preguicoso;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjetoPreguicoso<T> {
	private Class<T> klass;
	private List<CampoPreguisoso> campos;
	private List<ParametroPreguicoso> parametros;

	public ObjetoPreguicoso(Class<T> klass) {
		this.klass = klass;
		this.campos = new ArrayList<>();
		this.parametros = new ArrayList<>();
	}

	public T instanciar() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		T objeto;
		if (parametros.isEmpty())
			objeto = klass.newInstance();
		else
			objeto = instanciarComConstrutor();
		for (CampoPreguisoso campo : campos) {
			campo.configure(objeto);
		}
		return objeto;
	}

	private T instanciarComConstrutor() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<?>[] construtores = klass.getDeclaredConstructors();
		for (Constructor<?> constructor : construtores) {
			Class<?>[] parametrosDoConstrutor = constructor.getParameterTypes();
			if (parametrosDoConstrutor.length == parametros.size()) {
				Object[] initargs = new Object[parametrosDoConstrutor.length];
				for (int i = 0; i < parametrosDoConstrutor.length; i++) {
						
				}
				constructor.newInstance(initargs);
			}
		}
		throw new RuntimeException("Without constructor");
	}

	public void adicionar(CampoPreguisoso campo) {
		campos.add(campo);

	}

	public void adicionar(ParametroPreguicoso parametro) {
		parametros.add(parametro);
	}

}
