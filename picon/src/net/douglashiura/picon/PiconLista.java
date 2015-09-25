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

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class PiconLista<T> extends Picon<T, List<T>> {
	private List<T> entidades = new ArrayList<T>();
	public PiconLista(Class<T> classe, Deque<Parte> emChave, PiconStore contexto) throws Exception {
		super(contexto);
		Reflexao<T> socka = new Reflexao<T>(classe);
		emChave.pop().value();
		while (!emChave.peek().value().equals("}")) {
			String uid = emChave.pop().value();
			if (uid.equals("#")) {
				Parte uidtoke = emChave.pop();
				engatilhar(contexto, uidtoke);
			} else {
				T umObjeto = montaQualificador(classe, emChave, contexto, socka);
				entidades.add(umObjeto);
				contexto.add(uid, umObjeto);
			}

		}
		emChave.pop();
	}

	private void engatilhar(PiconStore contexto, final Parte uid) {
		contexto.addReferencia(new Vinculo() {
			@SuppressWarnings("unchecked")
			public void processar(Map<String, Object> referenciaveis) {
				entidades.add((T) referenciaveis.get(uid.value()));
			}

			@Override
			public Parte getApelido() {
				return uid;
			}
		});
	}

	@Override
	public List<T> getObjeto() {
		return entidades;
	}

}
