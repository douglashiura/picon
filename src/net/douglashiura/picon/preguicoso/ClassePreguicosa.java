package net.douglashiura.picon.preguicoso;

import java.util.HashMap;

public class ClassePreguicosa<T> {
	private HashMap<String, ObjetoPreguicoso<T>> objetos;
	private Class<T> klasse;

	public ClassePreguicosa(Class<T> klasse) {
		this.klasse = klasse;
		objetos = new HashMap<>();
	}

	public ObjetoPreguicoso<T> registre(String qualificador) {
		ObjetoPreguicoso<T> objeto = objetos.get(qualificador);
		if (objeto == null) {
			objeto = new ObjetoPreguicoso<>(klasse);
			objetos.put(qualificador, objeto);
		}
		return objeto;
	}
}
