package net.douglashiura.picon.preguicoso;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public class CampoReferencia extends Campo {

	private String campo;
	private String qualificador;

	public CampoReferencia(String campo, String qualificador) {
		super(campo, qualificador);
		this.campo = campo;
		this.qualificador = qualificador;
	}

	@Override
	public void configure(Object objeto, Contexto contextoPreguisoso) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException, ParseException {
		Field campoDeclarado = objeto.getClass().getDeclaredField(campo);
		campoDeclarado.setAccessible(Boolean.TRUE);
		campoDeclarado.set(objeto, contextoPreguisoso.get(qualificador));
	}
}
