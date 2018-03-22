/*Douglas Hiura Longo 
 * 18 de Março de 2018.
 * */
package net.douglashiura.picon.preguicoso;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public class ParametroReferecia implements Parametro {

	private String valor;

	public ParametroReferecia(String valor) {
		this.valor = valor;

	}

	@Override
	public Object getValor(Contexto contexto)
			throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, NoSuchMethodException, InvocationTargetException, ParseException {
		return contexto.get(valor);
	}

	@Override
	public String getValorDeclarado() {
		return valor;
	}

}
