package net.douglashiura.picon.preguicoso;

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

	public T instanciar(ContextoPreguisoso contexto) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
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

	private T instanciarComConstrutor() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<?>[] barnabe = new Class<?>[parametros.size()];
		Object[] barney = new Object[parametros.size()];
		for (int i = 0; i < parametros.size(); i++) {
			ParametroPreguicoso parametro = parametros.get(0);
			barnabe[i] = parametro.getKlass();
			barney[i] = parametro.getValor();
		}
		return klass.getDeclaredConstructor(barnabe).newInstance(barney);
	}

	public void adicionar(CampoPreguisoso campo) {
		campos.add(campo);

	}

	public void adicionar(ParametroPreguicoso parametro) {
		parametros.add(parametro);
	}
}