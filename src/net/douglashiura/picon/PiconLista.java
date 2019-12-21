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
		Reflexao<T> instanciador = new Reflexao<T>(classe);
		emChave.pop().valor();
		while (!emChave.peek().valor().equals("}")) {
			String qualificador = emChave.pop().valor();
			if (qualificador.equals("#")) {
				Parte qualificadorToke = emChave.pop();
				engatilhar(contexto, qualificadorToke);
			} else {
				T umObjeto = montaQualificador(classe, emChave, contexto, instanciador);
				entidades.add(umObjeto);
				contexto.adicionar(qualificador, umObjeto);
			}

		}
		emChave.pop();
	}

	private void engatilhar(PiconStore contexto, final Parte qualificador) {
		contexto.adicionarVinculo(new Vinculo() {
			@SuppressWarnings("unchecked")
			public void processar(Map<String, Object> referenciaveis) {
				entidades.add((T) referenciaveis.get(qualificador.valor()));
			}

			@Override
			public Parte getApelido() {
				return qualificador;
			}
		});
	}

	@Override
	public List<T> obterObjeto() {
		return entidades;
	}

}
