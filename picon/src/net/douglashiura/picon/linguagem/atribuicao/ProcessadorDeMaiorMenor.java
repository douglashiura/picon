package net.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;

public class ProcessadorDeMaiorMenor {

	public ProcessadorDeMaiorMenor(ObjetoPreguicoso<?> objetoPreguicoso) {
		// TODO Auto-generated constructor stub
	}

	public void processar(Deque<Parte> emMenor) {
		while (!">".equals(emMenor.peek().valor())) {
			emMenor.pop();
		}
	}

}
