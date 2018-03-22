package net.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.CampoPreguisoso;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;

public class ProcessaAtribuicoes {

	private Qualificadores qualificadores;

	public ProcessaAtribuicoes(Qualificadores qualificadores) {
		this.qualificadores = qualificadores;
	}

	public void processar(Deque<Parte> inicioDeColchetes, ObjetoPreguicoso<?> objetoPreguicoso) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		inicioDeColchetes.pop();// queima [
		while (!"]".equals(inicioDeColchetes.peek().valor())) {
			Parte campo = inicioDeColchetes.pop();
			Parte valor = inicioDeColchetes.pop();
			Atribuicoes atribuicao = Atribuicoes.deAtributo(valor.valor(), inicioDeColchetes.peek().valor());
			inicioDeColchetes.push(valor);
			CampoPreguisoso comando = atribuicao.processar(inicioDeColchetes, campo.valor(), objetoPreguicoso, qualificadores);
			objetoPreguicoso.adicionar(comando);
		}
		inicioDeColchetes.pop();
	}
}
