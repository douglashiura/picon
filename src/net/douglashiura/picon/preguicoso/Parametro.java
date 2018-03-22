package net.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public interface Parametro {




	Object getValor(Contexto contexto) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, ParseException;

	String getValorDeclarado();

}
