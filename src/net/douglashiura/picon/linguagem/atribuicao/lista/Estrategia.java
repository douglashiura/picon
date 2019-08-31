package net.douglashiura.picon.linguagem.atribuicao.lista;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.preguicoso.Contexto;

public abstract class Estrategia {
	private List<String> parametros;

	public Estrategia() {
		parametros = new LinkedList<String>();
	}

	public abstract void processar(Deque<Parte> pilha) throws ProblemaDeCompilacaoException;

	public void adicionar(String valor) {
		parametros.add(valor);
	}

	public List<String> getParametros() {
		return parametros;
	}

	abstract public Object valor(Class<?> type, Contexto contexto) throws ProblemaDeCompilacaoException;
}
