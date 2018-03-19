/*
 * Douglas Hiura Longo, 06 de Junho de 2014.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package net.douglashiura.picon.linguagem;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class PiconListaPrimitiva extends Picon {

	private List<Object> entidades = new ArrayList<Object>();

	public PiconListaPrimitiva(Class<?> classe, Deque<Parte> emChave, Qualificadores contexto) throws Exception {
		super(contexto);
		emChave.pop().valor();
		while (!emChave.peek().valor().equals("}")) {
			String uid = emChave.pop().valor();
			Object[] enumValors = classe.getEnumConstants();
			for (Object t : enumValors) {
				if (t.toString().equals(uid))
					entidades.add(t);

			}
		}
		emChave.pop();
	}

	@SuppressWarnings("unchecked")
	public PiconListaPrimitiva(Deque<Parte> emChave, Qualificadores contexto) throws Exception {
		super(contexto);
		emChave.pop().valor();
		while (!emChave.peek().valor().equals("}")) {
			String uid = emChave.pop().valor();
			entidades.add(uid);

		}
		emChave.pop();
	}

}
