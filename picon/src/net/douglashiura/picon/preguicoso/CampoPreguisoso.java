package net.douglashiura.picon.preguicoso;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class CampoPreguisoso {

	private String valor;
	private String campo;

	public CampoPreguisoso(String campo, String valor) {
		this.campo = campo;
		this.valor = valor;

	}

	public void configure(Object objeto) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
		Field campoDeclarado = objeto.getClass().getDeclaredField(campo);
		campoDeclarado.setAccessible(Boolean.TRUE);
		Constructor<?> c = campoDeclarado.getType().getDeclaredConstructor(String.class);
		campoDeclarado.set(objeto, c.newInstance(valor));
	}

}
