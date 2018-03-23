/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package net.douglashiura.picon.linguagem;

import java.util.ArrayDeque;
import java.util.Deque;

public class Partes {
	private static char QUEBRA_LINHA = '\n';
	private static char[] QUEBRA = { ' ', QUEBRA_LINHA, '	', '=', ';' };
	private static char[] RESERVADO = { '{', '}', '*', '[', ']', '#', '<', '>' };
	private static char ESCAPAR = '\'';

	public static Deque<Parte> explodir(String texto) {
		Deque<Parte> tokens = new ArrayDeque<Parte>();
		Parte anterior = null;
		int linha = 1;
		String esteToke = "";
		boolean escapando = false;
		for (char e : texto.toCharArray()) {
			if (ehNormal(e, escapando)) {
				if (ehToke(e, RESERVADO)) {
					if (!esteToke.isEmpty()) {
						tokens.add(anterior = new Parte(esteToke, anterior, linha));
					}
					tokens.add(anterior = new Parte(new String(new char[] { e }), anterior, linha));
					esteToke = "";
				} else if (!ehToke(e, QUEBRA)) {
					esteToke += e;
				} else if (!esteToke.isEmpty()) {
					tokens.add(anterior = new Parte(esteToke, anterior, linha));
					esteToke = "";
				}
			} else {
				if (!escapando && e == ESCAPAR) {
					if (!esteToke.isEmpty()) {
						tokens.add(anterior = new Parte(esteToke, anterior, linha));
						esteToke = "";
					}
					escapando = true;
				} else if (escapando && e != ESCAPAR) {
					esteToke += e;
				} else if (escapando && e == ESCAPAR) {
					tokens.add(anterior = new Parte(esteToke, anterior, linha));
					esteToke = "";
					escapando = false;
				}
			}
			linha = ehQuebraLinha(e) ? linha + 1 : linha;
		}
		if (!esteToke.isEmpty()) {
			tokens.add(anterior = new Parte(esteToke, anterior, linha));
		}
		return tokens;
	}

	private static boolean ehQuebraLinha(char e) {
		return e == QUEBRA_LINHA;
	}

	private static boolean ehNormal(char e, boolean escapando) {
		return e != ESCAPAR && !escapando;
	}

	private static boolean ehToke(char e, char[] chars) {
		for (char quebra : chars) {
			if (quebra == e) {
				return true;
			}
		}
		return false;
	}

}
