package net.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import net.douglashiura.picon.linguagem.Parte;

public class ProcessadorDeLista {

	private Class<?> klass;
	private Estrategia estrategia;

	public ProcessadorDeLista(String klassName) throws ClassNotFoundException {
		klass = Class.forName(klassName);
		if (klass.isEnum()) {
			estrategia = new EstrategiaEnumerado();
		} else {
			estrategia = new EstrategiaLista();
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

	interface Estrategia {

		void processar(Deque<Parte> pilha);

		String getValores();
	}

	class EstrategiaLista implements Estrategia {

		@Override
		public void processar(Deque<Parte> emInicioChaves) {
			emInicioChaves.pop();

		}

		@Override
		public String getValores() {
			return "";
		}
	}

	class EstrategiaEnumerado implements Estrategia {
		public String getValores() {
			return null;
		}

		public void processar(Deque<Parte> emInicioDeChaves) {
			emInicioDeChaves.pop();

		}
	}

}
