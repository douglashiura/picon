package net.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Carregadores;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.linguagem.atribuicao.lista.Estrategia;
import net.douglashiura.picon.linguagem.atribuicao.lista.EstrategiaReferencia;
import net.douglashiura.picon.linguagem.atribuicao.lista.EstrategiaValor;

public class ProcessadorDeLista {

	private Class<?> klass;
	private Estrategia estrategia;
	public Qualificadores contexto;

	public ProcessadorDeLista(String klassName, Qualificadores contexto, Parte parte)
			throws ProblemaDeCompilacaoException {
		this.contexto = contexto;
		try {
			klass = Carregadores.buscarClasse(klassName);
		} catch (ClassNotFoundException e) {
			throw new ProblemaDeCompilacaoException(e, parte);
		}
		if (klass.isEnum() || String.class.equals(klass)) {
			estrategia = new EstrategiaValor(klass, parte);
		} else {
			estrategia = new EstrategiaReferencia(contexto, klass);
		}
	}

	public void processar(Deque<Parte> emInicioDeChaves) throws ProblemaDeCompilacaoException {
		emInicioDeChaves.pop();
		while (!"}".equals(emInicioDeChaves.peek().valor())) {
			estrategia.processar(emInicioDeChaves);
		}
		emInicioDeChaves.pop();
	}

	public Estrategia getEstrategia() {
		return estrategia;
	}

}
