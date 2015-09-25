/*
 * Douglas Hiura Longo, 06 de Junho de 2014.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package net.douglashiura.picon;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class PiconListaPrimitiva<T> extends Picon<T, List<T>> {

	private List<T> entidades = new ArrayList<T>();

	public PiconListaPrimitiva(Class<T> classe, Deque<Parte> emChave, PiconStore contexto) throws Exception {
		super(contexto);
		emChave.pop().value();
		while (!emChave.peek().value().equals("}")) {
			String uid = emChave.pop().value();
			T[] enumValors = classe.getEnumConstants();
			for (T t : enumValors) {
				if (t.toString().equals(uid))
					entidades.add(t);

			}
		}
		emChave.pop();
	}

	@SuppressWarnings("unchecked")
	public PiconListaPrimitiva(Deque<Parte> emChave, PiconStore contexto) throws Exception {
		super(contexto);
		emChave.pop().value();
		while (!emChave.peek().value().equals("}")) {
			String uid = emChave.pop().value();
			entidades.add((T) uid);

		}
		emChave.pop();
	}

	@Override
	public List<T> getObjeto() {
		return entidades;
	}

}
