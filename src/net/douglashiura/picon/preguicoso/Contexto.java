/*Douglas Hiura Longo 
 * 18 de Março de 2018.
 * */
package net.douglashiura.picon.preguicoso;

import java.util.HashMap;
import java.util.Map;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Qualificadores;

public class Contexto {

	private Map<String, Object> instancias;
	private Qualificadores qualificadores;

	public Contexto(Qualificadores qualificadores) {
		this.qualificadores = qualificadores;
		instancias = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String qualificador) throws ProblemaDeCompilacaoException {
		T objeto = (T) instancias.get(qualificador);
		if (objeto == null) {
			objeto = (T) qualificadores.get(qualificador).instanciar(this);
			instancias.put(qualificador, objeto);
		}
		return objeto;
	}

}
