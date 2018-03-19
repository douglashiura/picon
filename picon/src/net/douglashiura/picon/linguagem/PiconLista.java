/*
 * Douglas Hiura Longo, 14 de Setembro de 2010.
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

public class PiconLista extends Picon {
	private List<Object> entidades = new ArrayList<Object>();

	public PiconLista(Class classe, Deque<Parte> emChave, Qualificadores contexto) throws Exception {
		super(contexto);
		emChave.pop().valor();
		while (!emChave.peek().valor().equals("}")) {
			String qualificador = emChave.pop().valor();
			if (qualificador.equals("#")) {
				Parte qualificadorToke = emChave.pop();
				engatilhar(contexto, qualificadorToke);
			} else {
				Object umObjeto = montaQualificador(emChave, contexto);
				entidades.add(umObjeto);
				// contexto.adicionar(qualificador, umObjeto);
			}

		}
		emChave.pop();
	}

	private void engatilhar(Qualificadores contexto, final Parte qualificador) {
		// contexto.adicionarVinculo(new Vinculo() {
		// @SuppressWarnings("unchecked")
		// public void processar(Map<String, Object> referenciaveis) {
		// entidades.add((T) referenciaveis.get(qualificador.valor()));
		// }
		//
		// @Override
		// public Parte getApelido() {
		// return qualificador;
		// }
		// });
	}

}
