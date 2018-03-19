/*Douglas Hiura Longo 
 * 18 de Março de 2018.
 * */
package net.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;

public class ParametroRefereciaPreguicoso implements Parametro {

	private String valor;

	public ParametroRefereciaPreguicoso(String valor) {
		this.valor = valor;

	}

	@Override
	public Object getValor(ContextoPreguisoso contexto)
			throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		return contexto.get(valor);
	}

}
