// Douglas Hiura Longo, 21 março de 2018.
package net.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;

public class ProcessadorDeColchetes {

	public ProcessadorDeColchetes(ObjetoPreguicoso<?> objetoPreguicoso) {

	}

	public void processar(Deque<Parte> emColchetes) {
		while (!"]".equals(emColchetes.peek().valor())) {
			emColchetes.pop();
		}
	}

}
