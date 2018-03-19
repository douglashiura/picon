/*Douglas Hiura Longo 
 * 18 de Março de 2018.
 * */
package net.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjetoPreguicoso<T> {
	private Class<T> klass;
	private List<CampoPreguisoso> campos;
	private List<Parametro> parametros;

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
			objeto = instanciarComConstrutor(contexto);
		for (CampoPreguisoso campo : campos) {
			campo.configure(objeto);
		}
		return objeto;
	}

	private T instanciarComConstrutor(ContextoPreguisoso contexto) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		Class<?>[] barnabe = new Class<?>[parametros.size()];
		Object[] barney = new Object[parametros.size()];
		for (int i = 0; i < parametros.size(); i++) {
			Parametro parametro = parametros.get(i);
			Object valor = parametro.getValor(contexto);
			barney[i] = valor;
			barnabe[i] = valor.getClass();
		}
		return klass.getDeclaredConstructor(barnabe).newInstance(barney);
	}

	public void adicionar(CampoPreguisoso campo) {
		campos.add(campo);

	}

	public void adicionarParametro(Parametro parametro) {
		parametros.add(parametro);
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public List<CampoPreguisoso> getCampos() {
		return campos;
	}
}