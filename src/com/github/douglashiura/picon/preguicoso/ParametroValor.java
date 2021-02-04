/*Douglas Hiura Longo 
 * 18 de Março de 2018.
 * */
package com.github.douglashiura.picon.preguicoso;

public class ParametroValor implements Parametro {
	private String valor;

	public ParametroValor(String valor) {
		this.valor = valor;

	}

	private Object tentarConverter(String corda) {
		try {
			return Long.parseLong(corda);
		} catch (NumberFormatException e) {
			return corda;
		}
	}

	@Override

	public Object getValor(Contexto contextoPreguisoso) {
		return tentarConverter(valor);
	}

	@Override
	public String getValorDeclarado() {
		return valor;
	}

}
