package com.github.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import com.github.douglashiura.picon.ProblemaDeCompilacaoException;
import com.github.douglashiura.picon.linguagem.Carregadores;
import com.github.douglashiura.picon.linguagem.Parte;
import com.github.douglashiura.picon.linguagem.Qualificadores;
import com.github.douglashiura.picon.linguagem.atribuicao.lista.Estrategia;
import com.github.douglashiura.picon.linguagem.atribuicao.lista.EstrategiaReferencia;
import com.github.douglashiura.picon.linguagem.atribuicao.lista.EstrategiaValor;

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
