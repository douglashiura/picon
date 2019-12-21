/*Douglas Hiura Longo 
 * 18 de Março de 2018.
 * */
package net.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Parte;

public class Objeto<T> {
	private Class<T> klass;
	private List<Campo> campos;
	private List<Parametro> parametros;
	private Parte parte;

	public Objeto(Class<T> klass, Parte parte) {
		this.klass = klass;
		this.parte = parte;
		this.campos = new ArrayList<>();
		this.parametros = new ArrayList<>();
	}

	public T instanciar(Contexto contexto) throws ProblemaDeCompilacaoException {
		T objeto;
		if (parametros.isEmpty())
			try {
				objeto = klass.getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new ProblemaDeCompilacaoException(e, parte);
			}
		else
			objeto = instanciarComConstrutor(contexto);
		for (Campo campo : campos) {
			try {
				campo.configure(objeto, contexto);
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
					| InstantiationException | InvocationTargetException | ParseException e) {
				throw new ProblemaDeCompilacaoException(e, parte);
			}
		}
		return objeto;
	}

	private T instanciarComConstrutor(Contexto contexto) throws ProblemaDeCompilacaoException {
		Class<?>[] barnabe = new Class<?>[parametros.size()];
		Object[] barney = new Object[parametros.size()];
		for (int i = 0; i < parametros.size(); i++) {
			Parametro parametro = parametros.get(i);
			Object valor = parametro.getValor(contexto);
			barney[i] = valor;
			barnabe[i] = valor.getClass();
		}
		try {
			return klass.getDeclaredConstructor(barnabe).newInstance(barney);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new ProblemaDeCompilacaoException(e, parte);
		}
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