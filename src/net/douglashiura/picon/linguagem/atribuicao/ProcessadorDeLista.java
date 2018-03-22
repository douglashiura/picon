package net.douglashiura.picon.linguagem.atribuicao;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;

public class ProcessadorDeLista {

	private Class<?> klass;
	private Estrategia estrategia;
	public Qualificadores contexto;

	public ProcessadorDeLista(String klassName, Qualificadores contexto) throws ClassNotFoundException {
		this.contexto = contexto;
		klass = Class.forName(klassName);
		if (klass.isEnum() || String.class.equals(klass)) {
			estrategia = new EstrategiaParametro();
		} else {
			estrategia = new EstrategiaObjeto();
		}
	}

	public void processar(Deque<Parte> emInicioDeChaves) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		emInicioDeChaves.pop();
		while (!"}".equals(emInicioDeChaves.peek().valor())) {
			estrategia.processar(emInicioDeChaves);
		}
		emInicioDeChaves.pop();
	}

	public String getValores() {
		return estrategia.getValores();
	}

	interface Estrategia {
		void processar(Deque<Parte> pilha) throws NoSuchFieldException, SecurityException, ClassNotFoundException;

		String getValores();
	}

	class EstrategiaObjeto implements Estrategia {
		private List<String> parametros;

		public EstrategiaObjeto() {
			parametros = new ArrayList<>();
		}

		@Override
		public void processar(Deque<Parte> emInicioReferenciaOuObjeto) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
			Parte referenciaOuObjeto = emInicioReferenciaOuObjeto.pop();
			String valor = referenciaOuObjeto.valor();
			Atribuicoes atribuicao = Atribuicoes.deElementoDeLista(valor, emInicioReferenciaOuObjeto.peek().valor());
			emInicioReferenciaOuObjeto.push(referenciaOuObjeto);
			String qualificador = atribuicao.processarElementoDaLista(emInicioReferenciaOuObjeto, contexto, klass);
			parametros.add(qualificador);

		}

		@Override
		public String getValores() {
			return parametros.toString();
		}
	}

	class EstrategiaParametro implements Estrategia {
		private List<String> parametros;

		public EstrategiaParametro() {
			parametros = new ArrayList<>();
		}

		public String getValores() {
			return parametros.toString();
		}

		public void processar(Deque<Parte> emInicioDeChaves) {
			parametros.add(emInicioDeChaves.pop().valor());
		}
	}

}
