package net.douglashiura.picon.linguagem;

import java.util.Deque;

import net.douglashiura.picon.linguagem.atribuicao.Atribuicoes;
import net.douglashiura.picon.preguicoso.CampoPreguisoso;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;

public class ProcessaAtribuicoes {

	private Qualificadores qualificadores;

	public ProcessaAtribuicoes(Qualificadores qualificadores) {
		this.qualificadores = qualificadores;
	}

	public void processar(Deque<Parte> inicioDeColchetes, ObjetoPreguicoso<?> objetoPreguicoso) throws NoSuchFieldException, SecurityException {
		inicioDeColchetes.pop();// queima [
		while (!"]".equals(inicioDeColchetes.peek().valor())) {
			Parte campo = inicioDeColchetes.pop();
			Parte valor = inicioDeColchetes.pop();
			Atribuicoes atribuicao = Atribuicoes.sincronizar(valor.valor(), inicioDeColchetes.peek().valor());
			CampoPreguisoso comando = atribuicao.processar(inicioDeColchetes, campo.valor(), objetoPreguicoso, qualificadores);
			objetoPreguicoso.adicionar(comando);
		}
	}
}
