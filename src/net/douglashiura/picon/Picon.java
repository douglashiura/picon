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

<<<<<<< HEAD
import java.util.Deque;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.ProcessadorDeConjunto;
import net.douglashiura.picon.linguagem.Qualificadores;

public class Picon {
	public static Qualificadores explodir(Deque<Parte> iterator) throws ProblemaDeCompilacaoException {
		Qualificadores qualificadores = new Qualificadores();
		Parte parte = null;
		try {
			while (!iterator.isEmpty()) {
				parte = iterator.peek();
				ProcessadorDeConjunto processador = new ProcessadorDeConjunto(qualificadores);
				processador.processar(iterator);
			}
		} catch (NullPointerException incompleto) {
			throw new ProblemaDeCompilacaoException(incompleto, parte);
		}
		return qualificadores;
=======
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public abstract class Picon<T, O> {
	PiconStore contexto;

	Picon(PiconStore contexto) {
		this.contexto = contexto;
	}

	public abstract O obterObjeto();

	@SuppressWarnings("unchecked")
	final public synchronized static <T> PiconStore construir(Deque<Parte> emClasse)
			throws ProblemaDeCompilacao {
		PiconStore contexto = new PiconStore();
		while (!emClasse.isEmpty()) {
			try {
				Class<T> classe;
				classe = (Class<T>) Class.forName(emClasse.pop().valor());
				emClasse.pop();
				while (!emClasse.peek().valor().equals("}")) {
					String qualificador = emClasse.pop().valor();
					PiconAtributoEntidade<T> picon = new PiconAtributoEntidade<T>(
							classe, emClasse, contexto);
					contexto.adicionar(qualificador, picon.obterObjeto());
				}
				emClasse.pop();
			} catch (Exception e) {
				throw new ProblemaDeCompilacao(e, emClasse.element());
			}
		}
		contexto.soldar();
		return contexto;

	}

	final void processaAtributo(Deque<Parte> emColchetes, Reflexao<T> socka,
			T umObjeto) throws Exception {
		emColchetes.pop();
		while (!emColchetes.isEmpty()
				&& !emColchetes.peek().valor().equals("]"))
			escolha(emColchetes, socka, umObjeto);
		emColchetes.pop();
	}

	final private void escolha(Deque<Parte> emCampo, Reflexao<T> fundidor,
			T umObjeto) throws Exception {
		String campo = emCampo.pop().valor();
		String valor = emCampo.pop().valor();
		Parte toke = emCampo.peek();
		String proximo = toke.valor();
		switch (Escolha.qual(valor, proximo)) {
		case REFERENCIA:
			fazerReferencia(campo, emCampo, umObjeto, fundidor);
			break;
		case VALUE:
			try {
				fazerValor(fundidor, umObjeto, campo, valor);
			} catch (NullPointerException e) {
				throw new ProblemaDeCompilacao(null, toke);
			}
			break;
		case COMPOSTO:
			fazerAtributoEntidade(emCampo, fundidor, campo, umObjeto);
			break;
		case LISTA:
			fazerLista(emCampo, valor, campo, fundidor, umObjeto);
			break;
		}

	}

	final private void fazerValor(Reflexao<T> socka, T umObjeto, String label,
			String value) throws Exception {
		socka.criarPrimitivo(label, value, umObjeto);
	}

	@SuppressWarnings("unchecked")
	final private void fazerLista(Deque<Parte> emValor, String valor,
			String campo, Reflexao<T> refletor, T umObjeto) throws Exception {

		if ("String".equals(valor) || Class.forName(valor).isEnum()) {
			if ("String".equals(valor)) {
				PiconListaPrimitiva<String> picon = new PiconListaPrimitiva<String>(
						emValor, contexto);
				refletor.fixar(campo, (T) picon.obterObjeto(), umObjeto);
			} else {
				PiconListaPrimitiva<Object> picon = new PiconListaPrimitiva<Object>(
						(Class<Object>) Class.forName(valor), emValor, contexto);
				refletor.fixar(campo, (T) picon.obterObjeto(), umObjeto);
			}
		} else {
			PiconLista<Object> picon = new PiconLista<Object>(
					(Class<Object>) Class.forName(valor), emValor, contexto);
			refletor.fixar(campo, (T) picon.obterObjeto(), umObjeto);
		}

	}

	final private void fazerAtributoEntidade(Deque<Parte> emValor,
			Reflexao<T> socka, String label, T umObjeto) throws Exception {
		PiconAtributoEntidade<Object> entidades = new PiconAtributoEntidade<Object>(
				socka.getClasse(label), emValor, contexto);
		socka.fixar(label, entidades.obterObjeto(), umObjeto);

	}

	final private void fazerReferencia(final String campo,
			final Deque<Parte> valor, final T umObjeto, final Reflexao<T> instanciador) {
		final Parte toke = valor.pop();
		final String qualificador = toke.valor();
		contexto.adicionarVinculo(new Vinculo() {
			public void processar(Map<String, Object> referenciaveis)
					throws Exception {
				instanciador.fixar(campo, referenciaveis.get(qualificador), umObjeto);
			}

			@Override
			public Parte getApelido() {
				return toke;
			}
		});

	}

	T montaQualificador(Class<T> classe, Deque<Parte> emChave,
			PiconStore contexto, Reflexao<T> instanciador)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, Exception {
		T umObjeto;
		if (emChave.peek().valor().equals("<")) {
			umObjeto = criarObjetoComConstrutor(classe, emChave, contexto);
		} else {
			umObjeto = classe.newInstance();
		}
		processaAtributo(emChave, instanciador, umObjeto);
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
		umObjeto = classe.getDeclaredConstructor(barnabe).newInstance(barney);
		emChave.pop();
		return umObjeto;
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
>>>>>>> branch 'master' of https://github.com/douglashiura/picon.git
	}

}
