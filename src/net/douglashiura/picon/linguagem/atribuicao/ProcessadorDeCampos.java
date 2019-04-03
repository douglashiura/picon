package net.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.Campo;
import net.douglashiura.picon.preguicoso.Objeto;

public class ProcessadorDeCampos {

	private Qualificadores qualificadores;

	public ProcessadorDeCampos(Qualificadores qualificadores) {
		this.qualificadores = qualificadores;
	}

	public void processar(Deque<Parte> inicioDeColchetesOuMenor, Objeto<?> objeto)
			throws ProblemaDeCompilacaoException {
		String valorDecisao = inicioDeColchetesOuMenor.peek().valor();
		if ("<".equals(valorDecisao)) {
			new ProcessadorDeConstrutor(objeto).processar(inicioDeColchetesOuMenor);
		}
		inicioDeColchetesOuMenor.pop();
		while (!"]".equals(inicioDeColchetesOuMenor.peek().valor())) {
			Parte campo = inicioDeColchetesOuMenor.pop();
			Parte valor = inicioDeColchetesOuMenor.pop();
			Atribuicoes atribuicao = Atribuicoes.deAtributo(valor.valor(), inicioDeColchetesOuMenor.peek().valor());
			inicioDeColchetesOuMenor.push(valor);
			Campo comando = atribuicao.objeto(inicioDeColchetesOuMenor, campo.valor(), objeto, qualificadores);
			objeto.adicionar(comando);
		}
		inicioDeColchetesOuMenor.pop();
	}
}
