/*Douglas Hiura Longo 
 * 18 de Março de 2018.
 * */
package net.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import net.douglashiura.picon.linguagem.Qualificadores;

public class Contexto {

	private Map<String, Object> instancias;
	private Qualificadores qualificadores;

	public Contexto(Qualificadores qualificadores) {
		this.qualificadores = qualificadores;
		instancias = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String qualificador) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, ParseException {
		T objeto = (T) instancias.get(qualificador);
		if (objeto == null) {
			objeto = (T) qualificadores.get(qualificador).instanciar(this);
			instancias.put(qualificador, objeto);
		}
		return objeto;
	}

}
