package net.douglashiura.picon.linguagem.atribuicao.lista;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.preguicoso.Contexto;
import net.douglashiura.picon.preguicoso.Valores;

public class EstrategiaValor extends Estrategia {
	private Class<?> klasse;
	private Parte parte;

	public EstrategiaValor(Class<?> tipo, Parte parte) {
		klasse = tipo;
		this.parte = parte;
	}

	public void processar(Deque<Parte> emInicioDeChaves) {
		adicionar(emInicioDeChaves.pop().valor());
	}

	@Override
	public Object valor(Class<?> type, Contexto contexto) throws ProblemaDeCompilacaoException {
		List<String> parametros = getParametros();
		List<Object> objetos = new ArrayList<>(parametros.size());
		for (String valor : parametros) {
			Object objeto = Valores.de(klasse, valor, parte);
			objetos.add(objeto);
		}
		return objetos;
	}
}