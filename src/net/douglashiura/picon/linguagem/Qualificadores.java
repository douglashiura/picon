package net.douglashiura.picon.linguagem;

import java.util.HashMap;
import java.util.Map;

import net.douglashiura.picon.preguicoso.Objeto;

public class Qualificadores {

	private Map<String, Objeto<?>> qualificadores;

	public Qualificadores() {
		qualificadores = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <T> Objeto<T> get(String qualificador) {
		return (Objeto<T>) qualificadores.get(qualificador);
	}

	public void put(String qualificador, Objeto<?> objeto) {
		qualificadores.put(qualificador, objeto);
	}

}
