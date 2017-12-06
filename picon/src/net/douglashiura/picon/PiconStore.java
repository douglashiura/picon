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
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//anglo/latino
final public class PiconStore {

	private Map<String, Object> estoque = new HashMap<String, Object>();
	private List<Vinculo> vinculos = new ArrayList<Vinculo>();

	PiconStore() {}

	public static PiconStore build(Class<?> carregador, String... fragmentos) throws IOException, ProblemaDeCompilacao {
		StringBuffer all = new StringBuffer();
		for (String frag : fragmentos) {
			InputStream input = carregador.getResourceAsStream(frag);
			if (input == null)
				throw new RuntimeException(frag + " inexistente");
			byte[] arquivo = new byte[input.available()];
			input.read(arquivo);
			input.close();
			all.append(new String(arquivo));
		}

		return Picon.construir(new ArrayDeque<Parte>(new Fragmentador(all.toString()).getTokes()));
	}

	public static PiconStore build(String piconolos) throws IOException, ProblemaDeCompilacao {
		return Picon.construir(new ArrayDeque<Parte>(new Fragmentador(piconolos).getTokes()));
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
