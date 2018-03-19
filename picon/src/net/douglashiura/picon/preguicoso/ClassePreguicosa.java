/*Douglas Hiura Longo 
 * 18 de Março de 2018.
 * */
package net.douglashiura.picon.preguicoso;

import net.douglashiura.picon.linguagem.Qualificadores;

public class ClassePreguicosa<T> {

	private Class<T> klasse;
	private Qualificadores qualificadores;

	public ClassePreguicosa(Class<T> klasse, Qualificadores qualificadores) {
		this.klasse = klasse;
		this.qualificadores = qualificadores;
	}

	public ObjetoPreguicoso<T> registre(String qualificador) {
		ObjetoPreguicoso<T> objeto = qualificadores.get(qualificador);
		if (objeto == null) {
			objeto = new ObjetoPreguicoso<>(klasse);
			qualificadores.put(qualificador, objeto);
		}
		return objeto;
	}
}
