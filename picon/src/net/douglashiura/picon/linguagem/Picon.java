/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/doug
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package net.douglashiura.picon.linguagem;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import net.douglashiura.picon.ProblemaDeCompilacao;
import net.douglashiura.picon.preguicoso.ClassePreguicosa;

public abstract class Picon {
	Qualificadores contexto;

	Picon(Qualificadores contexto) {
		this.contexto = contexto;
	}

	public static void construir(Qualificadores qualificadores, ArrayDeque<Parte> emClasse)
			throws ProblemaDeCompilacao {
		while (!emClasse.isEmpty()) {
			try {
				Class<?> classe = (Class<?>) Class.forName(emClasse.pop().valor());
				emClasse.pop();
				ClassePreguicosa<?> classe1 = new ClassePreguicosa<>(classe, qualificadores);

				while (!emClasse.peek().valor().equals("}")) {

					String qualificador = emClasse.pop().valor();
					PiconAtributoEntidade picon = new PiconAtributoEntidade(emClasse, classe1, qualificadores);
					// contexto.adicionar(qualificador, picon.obterObjeto());
				}
				emClasse.pop();
			} catch (Exception e) {
				throw new ProblemaDeCompilacao(e, emClasse.element());
			}
		}

	}

	final void processaAtributo(Deque<Parte> emColchetes, Object umObjeto) throws Exception {
		emColchetes.pop();
		while (!emColchetes.isEmpty() && !emColchetes.peek().valor().equals("]"))
			escolha(emColchetes, umObjeto);
		emColchetes.pop();
	}

	final private void escolha(Deque<Parte> emCampo, Object umObjeto) throws Exception {
		String campo = emCampo.pop().valor();
		String valor = emCampo.pop().valor();
		Parte toke = emCampo.peek();
		String proximo = toke.valor();
		switch (Escolha.qual(valor, proximo)) {
		case REFERENCIA:
			fazerReferencia(campo, emCampo, umObjeto);
			break;
		case VALUE:
			try {
				fazerValor(umObjeto, campo, valor);
			} catch (NullPointerException e) {
				throw new ProblemaDeCompilacao(null, toke);
			}
			break;
		case COMPOSTO:
			fazerAtributoEntidade(emCampo, campo, umObjeto);
			fazerLista(emCampo, valor, campo, umObjeto);
			break;
		case LISTA:
			break;
		}

	}

	final private void fazerValor(Object umObjeto, String label, String value) throws Exception {

	}

	@SuppressWarnings("unchecked")
	final private void fazerLista(Deque<Parte> emValor, String valor, String campo, Object umObjeto) throws Exception {

		if ("String".equals(valor) || Class.forName(valor).isEnum()) {
			if ("String".equals(valor)) {
				PiconListaPrimitiva picon = new PiconListaPrimitiva(emValor, contexto);
				// refletor.fixar(campo, picon.obterObjeto(), umObjeto);
			} else {
				PiconListaPrimitiva picon = new PiconListaPrimitiva((Class<Object>) Class.forName(valor), emValor,
						contexto);
				// refletor.fixar(campo, picon.obterObjeto(), umObjeto);
			}
		} else {
			PiconLista picon = new PiconLista((Class) Class.forName(valor), emValor, contexto);

		}

	}

	final private void fazerAtributoEntidade(Deque<Parte> emValor, String label, Object umObjeto) throws Exception {
		ClassePreguicosa<?> aa = new ClassePreguicosa<>(Class.forName(label), contexto);
		PiconAtributoEntidade entidades = new PiconAtributoEntidade(emValor, aa, contexto);

	}

	final private void fazerReferencia(final String campo, final Deque<Parte> valor, final Object umObjeto) {
		final Parte toke = valor.pop();
		final String qualificador = toke.valor();
		// contexto.adicionarVinculo(new Vinculo() {
		// public void processar(Map<String, Object> referenciaveis)
		// throws Exception {
		// instanciador.fixar(campo, referenciaveis.get(qualificador),
		// umObjeto);
		// }
		//
		// @Override
		// public Parte getApelido() {
		// return toke;
		// }
		// });

	}

	Object montaQualificador(Deque<Parte> emChave, Qualificadores contexto) throws InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		Object umObjeto = null;
		if (emChave.peek().valor().equals("<")) {
			umObjeto = criarObjetoComConstrutor(emChave, contexto);
		} else {

		}
		processaAtributo(emChave, umObjeto);
		return umObjeto;
	}

	Object criarObjetoComConstrutor(Deque<Parte> emChave, Qualificadores contexto)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object umObjeto;
		List<Class<?>> classes = new ArrayList<Class<?>>();
		List<Object> objetos = new ArrayList<Object>();
		emChave.pop();
		while (!emChave.peek().valor().equals(">")) {
			if (emChave.peek().valor().equals("#")) {
				emChave.pop();
				Parte qualificador = emChave.pop();
				Object objeto = contexto.get(qualificador.valor());
				objetos.add(objeto);
				classes.add(objeto.getClass());
			} else {
				Parte parte = emChave.pop();
				Object valor = novo(parte.valor());
				objetos.add(valor);
				classes.add(valor.getClass());
			}
		}

		Class<?>[] barnabe = new Class<?>[classes.size()];
		Object[] barney = new Object[objetos.size()];
		classes.toArray(barnabe);
		objetos.toArray(barney);
		primitivos(barnabe);

		emChave.pop();
		return null;
	}

	private void primitivos(Class<?>[] classes) {
		for (int i = 0; i < classes.length; i++)
			if (classes[i].equals(Long.class))
				classes[i] = Long.TYPE;
	}

	private Object novo(String corda) {
		try {
			return Long.parseLong(corda);

		} catch (NumberFormatException e) {
			return corda;
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
