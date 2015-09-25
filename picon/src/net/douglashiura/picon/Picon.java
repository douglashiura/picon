/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/doug
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package net.douglashiura.picon;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public abstract class Picon<T, O> {
	PiconStore master;

	Picon(PiconStore contexto) {
		master = contexto;
	}

	public abstract O getObjeto();

	@SuppressWarnings("unchecked")
	final public synchronized static <T> PiconStore build(Deque<Parte> emClasse)
			throws ExceptionCompilacao {
		PiconStore contexto = new PiconStore();
		while (!emClasse.isEmpty()) {
			try {
				Class<T> classe;
				classe = (Class<T>) Class.forName(emClasse.pop().value());
				emClasse.pop();
				while (!emClasse.peek().value().equals("}")) {
					String qualificador = emClasse.pop().value();
					PiconAtributoEntidade<T> picon = new PiconAtributoEntidade<T>(
							classe, emClasse, contexto);
					contexto.add(qualificador, picon.getObjeto());
				}
				emClasse.pop();
			} catch (Exception e) {
				throw new ExceptionCompilacao(e, emClasse.element());
			}
		}
		contexto.soldar();
		return contexto;

	}

	final void processaAtributo(Deque<Parte> emColchetes, Reflexao<T> socka,
			T umObjeto) throws Exception {
		emColchetes.pop();
		while (!emColchetes.isEmpty()
				&& !emColchetes.peek().value().equals("]"))
			escolha(emColchetes, socka, umObjeto);
		emColchetes.pop();
	}

	final private void escolha(Deque<Parte> emLabel, Reflexao<T> fundidor,
			T umObjeto) throws Exception {
		String label = emLabel.pop().value();
		String value = emLabel.pop().value();
		Parte toke = emLabel.peek();
		String proximo = toke.value();
		switch (Escolha.qual(value, proximo)) {
		case REFERENCIA:
			fazerReferencia(label, emLabel, umObjeto, fundidor);
			break;
		case VALUE:
			try {
				fazerValor(fundidor, umObjeto, label, value);
			} catch (NullPointerException e) {
				throw new ExceptionCompilacao(null, toke);
			}
			break;
		case COMPOSTO:
			fazerAtributoEntidade(emLabel, fundidor, label, umObjeto);
			break;
		case LISTA:
			fazerLista(emLabel, value, label, fundidor, umObjeto);
			break;
		}

	}

	final private void fazerValor(Reflexao<T> socka, T umObjeto, String label,
			String value) throws Exception {
		socka.criarPrimitivo(label, value, umObjeto);
	}

	@SuppressWarnings("unchecked")
	final private void fazerLista(Deque<Parte> emValue, String value,
			String label, Reflexao<T> reflect, T umObjeto) throws Exception {

		if ("String".equals(value) || Class.forName(value).isEnum()) {
			if ("String".equals(value)) {
				PiconListaPrimitiva<String> picon = new PiconListaPrimitiva<String>(
						emValue, master);
				reflect.fixar(label, (T) picon.getObjeto(), umObjeto);
			} else {
				PiconListaPrimitiva<Object> picon = new PiconListaPrimitiva<Object>(
						(Class<Object>) Class.forName(value), emValue, master);
				reflect.fixar(label, (T) picon.getObjeto(), umObjeto);
			}
		} else {
			PiconLista<Object> picon = new PiconLista<Object>(
					(Class<Object>) Class.forName(value), emValue, master);
			reflect.fixar(label, (T) picon.getObjeto(), umObjeto);
		}

	}

	final private void fazerAtributoEntidade(Deque<Parte> emValue,
			Reflexao<T> socka, String label, T umObjeto) throws Exception {
		PiconAtributoEntidade<Object> pi = new PiconAtributoEntidade<Object>(
				socka.getClasse(label), emValue, master);
		socka.fixar(label, pi.getObjeto(), umObjeto);

	}

	final private void fazerReferencia(final String label,
			final Deque<Parte> value, final T umObjeto, final Reflexao<T> socka) {
		final Parte toke = value.pop();
		final String uid = toke.value();
		master.addReferencia(new Vinculo() {
			public void processar(Map<String, Object> referenciaveis)
					throws Exception {
				socka.fixar(label, referenciaveis.get(uid), umObjeto);
			}

			@Override
			public Parte getApelido() {
				return toke;
			}
		});

	}

	T montaQualificador(Class<T> classe, Deque<Parte> emChave,
			PiconStore contexto, Reflexao<T> socka)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, Exception {
		T umObjeto;
		if (emChave.peek().value().equals("<")) {
			umObjeto = criarObjetoComConstrutor(classe, emChave, contexto);
		} else {
			umObjeto = classe.newInstance();
		}
		processaAtributo(emChave, socka, umObjeto);
		return umObjeto;
	}

	T criarObjetoComConstrutor(Class<T> classe, Deque<Parte> emChave,
			PiconStore contexto) throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		T umObjeto;
		List<Class<?>> classes = new ArrayList<Class<?>>();
		List<Object> objetos = new ArrayList<Object>();
		emChave.pop();
		while (!emChave.peek().value().equals(">")) {
			if (emChave.peek().value().equals("#")) {
				emChave.pop();
				Parte qualificador = emChave.pop();
				Object objeto = contexto.get(qualificador.value());
				objetos.add(objeto);
				classes.add(objeto.getClass());
			} else {
				Parte string = emChave.pop();
				Object valor = novo(string.value());
				objetos.add(valor);
				classes.add(valor.getClass());
			}
		}

		Class<?>[] barnabe = new Class<?>[classes.size()];
		Object[] barney = new Object[objetos.size()];
		classes.toArray(barnabe);
		objetos.toArray(barney);
		primitivos(barnabe);
		umObjeto = classe.getDeclaredConstructor(barnabe).newInstance(barney);

		emChave.pop();
		return umObjeto;
	}

	private void primitivos(Class<?>[] barnabe) {
		for (int i = 0; i < barnabe.length; i++)
			if (barnabe[i].equals(Long.class))
				barnabe[i] = Long.TYPE;
	}

	private Object novo(String string) {
		try {
			return Long.parseLong(string);

		} catch (NumberFormatException e) {
			return string;
		}
	}

	public enum Escolha {
		REFERENCIA, VALUE, COMPOSTO, LISTA;
		final public static Escolha qual(String value, String proximo) {
			if (value.equals("#"))
				return REFERENCIA;
			else if (proximo.equals("[") || proximo.equals("<"))
				return COMPOSTO;
			else if (proximo.equals("{"))
				return LISTA;
			else
				return VALUE;
		}
	}

}
