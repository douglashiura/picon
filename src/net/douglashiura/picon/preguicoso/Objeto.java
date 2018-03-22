/*Douglas Hiura Longo 
 * 18 de Março de 2018.
 * */
package net.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Objeto<T> {
	private Class<T> klass;
	private List<Campo> campos;
	private List<Parametro> parametros;

	public Objeto(Class<T> klass) {
		this.klass = klass;
		this.campos = new ArrayList<>();
		this.parametros = new ArrayList<>();
	}

	public T instanciar(Contexto contexto) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, ParseException {
		T objeto;
		if (parametros.isEmpty())
			objeto = klass.newInstance();
		else
			objeto = instanciarComConstrutor(contexto);
		for (Campo campo : campos) {
			campo.configure(objeto,contexto);
		}
		return objeto;
	}

	private T instanciarComConstrutor(Contexto contexto) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, ParseException {
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

	public void adicionar(Campo campo) {
		campos.add(campo);

	}

	public void adicionarParametro(Parametro parametro) {
		parametros.add(parametro);
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public Class<T> getKlasse() {
		return klass;
	}
}