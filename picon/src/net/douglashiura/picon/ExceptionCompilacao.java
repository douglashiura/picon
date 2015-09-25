package net.douglashiura.picon;

public class ExceptionCompilacao extends Exception {
	private static final long serialVersionUID = 4692322657987854120L;
	private Parte toke;

	public ExceptionCompilacao(Throwable throwable, Parte t) {
		super(throwable);
		toke = t;
	}

	@Override
	public String getMessage() {
		return "Em linha " + toke.getLinha() + " com \"" + tokes() + "\"";
	}

	public String tokes() {
		String string = "";
		Parte tokeT = toke;
		int i = 3;
		while (tokeT != null && 0 < i--) {
			string = tokeT.value() + " " + string;
			tokeT = tokeT.getAnterior();
		}
		return string;
	}

	public Parte getToke() {
		return toke;
	}
}
