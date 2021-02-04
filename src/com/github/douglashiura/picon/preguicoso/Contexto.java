/*Douglas Hiura Longo 
 * 18 de Março de 2018.
 * */
package com.github.douglashiura.picon.preguicoso;

import java.util.HashMap;
import java.util.Map;

import com.github.douglashiura.picon.ProblemaDeCompilacaoException;
import com.github.douglashiura.picon.linguagem.Qualificadores;

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
