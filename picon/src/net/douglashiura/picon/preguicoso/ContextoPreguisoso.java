package net.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ContextoPreguisoso {

	private Map<String, Object> instancias;
	private Qualificadores qualificadores;

	public ContextoPreguisoso(Qualificadores qualificadores) {
		this.qualificadores = qualificadores;
		instancias = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String qualificador) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		T objeto = (T) instancias.get(qualificador);
		if (objeto == null) {
			objeto = (T) qualificadores.get(qualificador).instanciar(this);
			instancias.put(qualificador, objeto);
		}
		return objeto;
	}

}
