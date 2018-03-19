/*
 * Douglas Hiura Longo, 14 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package net.douglashiura.picon;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.douglashiura.picon.linguagem.Fragmentador;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Picon;
import net.douglashiura.picon.linguagem.Qualificadores;

//anglo/latino
final public class PiconStore {

	private Map<String, Object> estoque = new HashMap<String, Object>();
	private List<Vinculo> vinculos = new ArrayList<Vinculo>();

	PiconStore() {
	}

	public static Qualificadores build(String piconolos) throws IOException, ProblemaDeCompilacao {
		Qualificadores qualificadores = new Qualificadores();
		ArrayDeque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(piconolos).getTokens());
		 Picon.construir(qualificadores, tokens);
		 return qualificadores;
	}

	void adicionar(String qualificador, Object objeto) {
		estoque.put(qualificador, objeto);
	};

	void adicionarVinculo(Vinculo vinculo) {
		vinculos.add(vinculo);
	}

	void soldar() throws ProblemaDeCompilacao {
		for (Vinculo link : vinculos) {
			try {
				link.processar((Map<String, Object>) estoque);
			} catch (Exception e) {
				throw new ProblemaDeCompilacao(e, link.getApelido());
			}
		}
	}

	public <Y> Y get(String qualifier, Class<Y> klass) {
		return get(qualifier);
	}

	@SuppressWarnings("unchecked")
	public <Y> Y get(String qualifier) {
		Object objeto = estoque.get(qualifier);
		return (Y) objeto;
	}

	public Map<String, Object> getStore() {
		return estoque;
	}

}
