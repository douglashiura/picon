/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/doug
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package com.github.douglashiura.picon;

import java.util.Deque;

import com.github.douglashiura.picon.linguagem.Parte;
import com.github.douglashiura.picon.linguagem.ProcessadorDeConjunto;
import com.github.douglashiura.picon.linguagem.Qualificadores;

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
	}

}
