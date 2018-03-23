package net.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.Campo;
import net.douglashiura.picon.preguicoso.Objeto;

public class ProcessaAtribuicoes {

	private Qualificadores qualificadores;

	public ProcessaAtribuicoes(Qualificadores qualificadores) {
		this.qualificadores = qualificadores;
	}

	public void processar(Deque<Parte> inicioDeColchetes, Objeto<?> objeto) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		inicioDeColchetes.pop();
		while (!"]".equals(inicioDeColchetes.peek().valor())) {
			Parte campo = inicioDeColchetes.pop();
			Parte valor = inicioDeColchetes.pop();
			Atribuicoes atribuicao = Atribuicoes.deAtributo(valor.valor(), inicioDeColchetes.peek().valor());
			inicioDeColchetes.push(valor);
			Campo comando = atribuicao.processarDoObjeto(inicioDeColchetes, campo.valor(), objeto, qualificadores);
			objeto.adicionar(comando);
		}
		inicioDeColchetes.pop();
	}
}
