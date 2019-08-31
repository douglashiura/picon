package net.douglashiura.picon.linguagem.atribuicao.lista;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.linguagem.atribuicao.Atribuicoes;
import net.douglashiura.picon.preguicoso.Contexto;

public class EstrategiaReferencia extends Estrategia {

	private Qualificadores qualificadores;
	private Class<?> klass;

	public EstrategiaReferencia(Qualificadores qualificadores, Class<?> klass) {
		this.qualificadores = qualificadores;
		this.klass = klass;
	}

	@Override
	public void processar(Deque<Parte> emInicioReferenciaOuObjeto) throws ProblemaDeCompilacaoException {
		Parte referenciaOuObjeto = emInicioReferenciaOuObjeto.pop();
		String valor = referenciaOuObjeto.valor();
		Atribuicoes atribuicao = Atribuicoes.deLista(valor, emInicioReferenciaOuObjeto.peek().valor());
		emInicioReferenciaOuObjeto.push(referenciaOuObjeto);
		String qualificador = atribuicao.lista(emInicioReferenciaOuObjeto, qualificadores, klass);
		adicionar(qualificador);
	}

	@Override
	public Object valor(Class<?> type, Contexto contexto) throws ProblemaDeCompilacaoException {
		List<String> parametros = getParametros();
		Collection<Object> objetos = tipoDeColecao(type, parametros.size());
		for (String qualificador : parametros) {
			Object objeto = contexto.get(qualificador);
			objetos.add(objeto);
		}
		return objetos;
	}

	private Collection<Object> tipoDeColecao(Class<?> type, Integer quantos) {
		return Set.class.equals(type) ? new HashSet<>(quantos) : new ArrayList<>(quantos);
	}
}
