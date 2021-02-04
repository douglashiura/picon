/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package com.github.douglashiura.picon.linguagem;

import java.util.ArrayDeque;
import java.util.Deque;

public class Partes {
	private static char QUEBRA_LINHA = '\n';
	private static char[] QUEBRA = { ' ','	', QUEBRA_LINHA, ',','\r', '=', ';' };
	private static char[] RESERVADO = { '{', '}', '*', '[', ']', '#', '<', '>' };
	private static char ESCAPAR = '\'';

	public static Deque<Parte> explodir(String texto) {
		Deque<Parte> tokens = new ArrayDeque<Parte>();
		Parte anterior = null;
		int linha = 1;
		String esteToke = "";
		boolean escapando = false;
		for (char entrada : texto.toCharArray()) {
			if (ehNormal(entrada, escapando)) {
				if (ehToke(entrada, RESERVADO)) {
					if (!esteToke.isEmpty()) {
						tokens.add(anterior = new Parte(esteToke, anterior, linha));
					}
					tokens.add(anterior = new Parte(new String(new char[] { entrada }), anterior, linha));
					esteToke = "";
				} else if (!ehToke(entrada, QUEBRA)) {
					esteToke += entrada;
				} else if (!esteToke.isEmpty()) {
					tokens.add(anterior = new Parte(esteToke, anterior, linha));
					esteToke = "";
				}
			} else {
				if (!escapando && entrada == ESCAPAR) {
					if (!esteToke.isEmpty()) {
						tokens.add(anterior = new Parte(esteToke, anterior, linha));
						esteToke = "";
					}
					escapando = true;
				} else if (escapando && entrada != ESCAPAR) {
					esteToke += entrada;
				} else if (escapando && entrada == ESCAPAR) {
					tokens.add(anterior = new Parte(esteToke, anterior, linha));
					esteToke = "";
					escapando = false;
				}
			}
			linha = ehQuebraLinha(entrada) ? linha + 1 : linha;
		}
		if (!esteToke.isEmpty()) {
			tokens.add(anterior = new Parte(esteToke, anterior, linha));
		}
		return tokens;
	}

	private static boolean ehQuebraLinha(char entrada) {
		return entrada == QUEBRA_LINHA;
	}

	private static boolean ehNormal(char entrada, boolean escapando) {
		return entrada != ESCAPAR && !escapando;
	}

	private static boolean ehToke(char entrada, char[] chars) {
		for (char quebra : chars) {
			if (quebra == entrada) {
				return true;
			}
		}
		return false;
	}

}
