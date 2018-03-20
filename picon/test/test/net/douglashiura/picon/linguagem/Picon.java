package test.net.douglashiura.picon.linguagem;

import java.util.Deque;

import net.douglashiura.picon.linguagem.Atribuicoes;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.CampoPreguisoso;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;

public class Picon {

	private Qualificadores qualificadores;

	public Picon(Qualificadores qualificadores) {
		this.qualificadores = qualificadores;
	}

	public void processarAtribuicoes(Deque<Parte> inicioDeColchetes, ObjetoPreguicoso<?> objetoPreguicoso) {
		inicioDeColchetes.pop();// queima [
		while (!"]".equals(inicioDeColchetes.peek().valor())) {
			Parte campo = inicioDeColchetes.pop();
			Parte valor = inicioDeColchetes.pop();
			Atribuicoes atribuicao = Atribuicoes.qual(valor.valor(), inicioDeColchetes.peek().valor());
			CampoPreguisoso comando = atribuicao.processar(inicioDeColchetes, valor.valor(), campo.valor(), objetoPreguicoso, qualificadores);
			objetoPreguicoso.adicionar(comando);
		}
	}
}
