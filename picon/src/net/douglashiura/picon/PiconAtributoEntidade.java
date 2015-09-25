/*
 * Douglas Hiura Longo, 14 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package net.douglashiura.picon;

import java.util.Deque;

public class PiconAtributoEntidade<T> extends Picon<T, T> {
	private T umObjeto;

	public PiconAtributoEntidade(Class<T> classe, Deque<Parte> emColchetesComecoClasse, PiconStore contexto) throws Exception {
		this(classe, new Reflexao<T>(classe), emColchetesComecoClasse, contexto);
	}

	public PiconAtributoEntidade(Class<T> classe, Reflexao<T> reflexao, Deque<Parte> emColchetesComecoClasse, PiconStore contexto)
			throws Exception {
		super(contexto);
		umObjeto = montaQualificador(classe, emColchetesComecoClasse, contexto, reflexao);

	}

	public T getObjeto() {
		return umObjeto;
	}
}
