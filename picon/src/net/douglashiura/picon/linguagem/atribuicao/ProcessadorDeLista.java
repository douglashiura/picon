package net.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import net.douglashiura.picon.linguagem.Parte;

public class ProcessadorDeLista {

	private Class<?> klass;
	private EstrategiaEnumerado estrategia;

	public ProcessadorDeLista(String klassName) throws ClassNotFoundException {
		klass = Class.forName(klassName);
		if (klass.isEnum()) {
			estrategia = new EstrategiaEnumerado();
		}
	}

	public void processar(Deque<Parte> emInicioDeChaves) {
		while (!"}".equals(emInicioDeChaves.peek().valor())) {
			estrategia.processar(emInicioDeChaves);
		}
		emInicioDeChaves.pop();
	}

	public String getValores() {
		return estrategia.getValores();
	}

	class EstrategiaEnumerado {
		public String getValores() {
			return null;
		}

		public void processar(Deque<Parte> emInicioDeChaves) {
			// TODO Auto-generated method stub
			
		}
	}

}
