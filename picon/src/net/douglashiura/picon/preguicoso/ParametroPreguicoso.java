package net.douglashiura.picon.preguicoso;

public class ParametroPreguicoso implements Parametro {
	private String valor;

	public ParametroPreguicoso(String valor) {
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

	public Object getValor(ContextoPreguisoso contextoPreguisoso) {
		return tentarConverter(valor);
	}

}
