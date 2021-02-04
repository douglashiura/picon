package com.github.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import com.github.douglashiura.picon.linguagem.Parte;
import com.github.douglashiura.picon.preguicoso.Objeto;
import com.github.douglashiura.picon.preguicoso.ParametroReferecia;
import com.github.douglashiura.picon.preguicoso.ParametroValor;

public class ProcessadorDeConstrutor {
	private Objeto<?> objeto;

	public ProcessadorDeConstrutor(Objeto<?> objeto) {
		this.objeto = objeto;
	}

	public  void processar(Deque<Parte> emMenor) {
		emMenor.pop();
		while (!">".equals(emMenor.peek().valor())) {
			String valor = emMenor.pop().valor();
			if ("#".equals(valor)) {
				objeto.adicionarParametro(new ParametroReferecia(emMenor.pop().valor()));
			} else {
				objeto.adicionarParametro(new ParametroValor(valor));
			}
		}
		emMenor.pop();
	}

}
