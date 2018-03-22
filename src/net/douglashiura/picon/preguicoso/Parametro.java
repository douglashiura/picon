package net.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;

public interface Parametro {




	Object getValor(ContextoPreguisoso contexto) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException;

}
