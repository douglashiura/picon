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

import java.util.Deque;

import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;

public class PiconAtributoEntidade extends Picon {

	public PiconAtributoEntidade(Deque<Parte> emColchetesComecoClasse, ObjetoPreguicoso<?> classe, Qualificadores contexto) throws Exception {
		super(contexto);
		Object umObjeto = montaQualificador(emColchetesComecoClasse, contexto);
	}

}
