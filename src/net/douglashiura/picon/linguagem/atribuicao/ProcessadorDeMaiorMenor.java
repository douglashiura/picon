package net.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.preguicoso.Objeto;
import net.douglashiura.picon.preguicoso.ParametroValor;
import net.douglashiura.picon.preguicoso.ParametroReferecia;

public class ProcessadorDeMaiorMenor {
	private Objeto<?> objetoPreguicoso;

	public ProcessadorDeMaiorMenor(Objeto<?> objetoPreguicoso) {
		this.objetoPreguicoso = objetoPreguicoso;
	}

	public void processar(Deque<Parte> emMenor) {
		emMenor.pop();
		while (!">".equals(emMenor.peek().valor())) {
			String valor = emMenor.pop().valor();
			if ("#".equals(valor)) {
				objetoPreguicoso.adicionarParametro(new ParametroReferecia(emMenor.pop().valor()));
			} else {
				objetoPreguicoso.adicionarParametro(new ParametroValor(valor));
			}
		}
		emMenor.pop();
	}

}
