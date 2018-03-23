package net.douglashiura.picon.linguagem;

import java.util.Deque;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.atribuicao.ProcessadorDeCampos;
import net.douglashiura.picon.preguicoso.Objeto;

public class ProcessadorDeConjunto {

	private Qualificadores qualificadores;

	public ProcessadorDeConjunto(Qualificadores qualificadores) {
		this.qualificadores = qualificadores;
	}

	public void processar(Deque<Parte> emClasse) throws ProblemaDeCompilacaoException {
		Parte parteClasse = emClasse.pop();
		try {
			Class<?> klasse = Class.forName(parteClasse.valor());
			ProcessadorDeCampos processador = new ProcessadorDeCampos(qualificadores);
			emClasse.pop();
			while (!"}".equals(emClasse.peek().valor())) {
				Parte qualificador = emClasse.pop();
				Objeto<?> objeto = new Objeto<>(klasse,parteClasse);
				qualificadores.put(qualificador.valor(), objeto);
				processador.processar(emClasse, objeto);
			}
			emClasse.pop();
		} catch (ClassNotFoundException e) {
			throw new ProblemaDeCompilacaoException(e, parteClasse);
		}

	}

}
