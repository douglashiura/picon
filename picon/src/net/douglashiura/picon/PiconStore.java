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

final public class PiconStore {

	private Map<String, Object> estoque = new HashMap<String, Object>();
	private List<Vinculo> referencias = new ArrayList<Vinculo>();

	PiconStore() {
	}

	class Tipo {
		public Tipo(String uid2, Class<? extends Object> class1, String s) {
			this.tipo = class1;
			this.uid = uid2;
			this.config = s;
		}

		Class<?> tipo;
		String uid;
		String config;
	}

	public static PiconStore build(Class<?> carregador, String... fragmentos) throws IOException, ExceptionCompilacao {
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

		return Picon.build(new ArrayDeque<Parte>(new Fragmentador(all.toString()).getTokes()));
	}

	public static PiconStore build(String piconolos) throws IOException, ExceptionCompilacao {
		return Picon.build(new ArrayDeque<Parte>(new Fragmentador(piconolos).getTokes()));
	}

	public void add(String uid, Object t) {
		estoque.put(uid, t);
	};

	public void addReferencia(Vinculo link) {
		referencias.add(link);
	}

	@SuppressWarnings("unchecked")
	public <Y> Y get(String uid) {

		Object a = estoque.get(uid);
		return (Y) a;
	}

	void soldar() throws ExceptionCompilacao {
		for (Vinculo link : referencias) {
			try {
				link.processar((Map<String, Object>) estoque);
			} catch (Exception e) {
				throw new ExceptionCompilacao(e, link.getApelido());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <Y> List<Y> list(Class<Y> classe) {
		List<Y> lista = new ArrayList<Y>();
		for (Object t : estoque.values())
			if (classe.equals(t.getClass()))
				lista.add((Y) t);
		return lista;
	}

	public <Y> Y get(String uid, Class<Y> klass) {
		return get(uid);
	}

	public Map<String, Object> getStore() {
		return estoque;
	}

}
