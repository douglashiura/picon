package net.douglashiura.picon.linguagem;

import java.util.HashMap;
import java.util.Map;

import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;

public class Qualificadores {

	private Map<String, ObjetoPreguicoso<?>> qualificadores;

	public Qualificadores() {
		qualificadores = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <T> ObjetoPreguicoso<T> get(String qualificador) {
		return (ObjetoPreguicoso<T>) qualificadores.get(qualificador);
	}

	public void put(String qualificador, ObjetoPreguicoso<?> objeto) {
		qualificadores.put(qualificador, objeto);
	}

}