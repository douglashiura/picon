// Douglas Hiura Longo, 12 de Setembro de 2010.
package net.douglashiura.picon.linguagem.atribuicao;

import java.util.Deque;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.Campo;
import net.douglashiura.picon.preguicoso.CampoReferencia;
import net.douglashiura.picon.preguicoso.CampoReferenciaLista;
import net.douglashiura.picon.preguicoso.CampoValor;
import net.douglashiura.picon.preguicoso.Objeto;

public enum Atribuicoes {
	INVALIDA {

		@Override
		public Campo objeto(Deque<Parte> emCampo, String campo, Objeto<?> objeto, Qualificadores contexto) {
			throw new RuntimeException();
		}
	},
	REFERENCIA {
		@Override
		public Campo objeto(Deque<Parte> emCadeia, String campo, Objeto<?> objeto, Qualificadores contexto) {
			emCadeia.pop();
			Parte parte = emCadeia.pop();
			return new CampoReferencia(campo, parte.valor(), parte);
		}

		@Override
		public String lista(Deque<Parte> emCadeia, Qualificadores contexto, Class<?> klass) {
			emCadeia.pop();
			return emCadeia.pop().valor();

		}
	},
	VALOR {
		@Override
		public Campo objeto(Deque<Parte> emValor, String campo, Objeto<?> objeto, Qualificadores contexto) {
			Parte parte = emValor.pop();
			return new CampoValor(campo, parte.valor(), parte);
		}
	},
	COMPOSTA {
		@Override
		public Campo objeto(Deque<Parte> emQualificador, String campo, Objeto<?> objeto, Qualificadores contexto)
				throws ProblemaDeCompilacaoException {
			Parte qualificador = emQualificador.pop();
			Objeto<?> objetoPreguicoso = classeDoCampo(campo, objeto.getKlasse(), emQualificador, contexto,
					qualificador);
			ProcessadorDeCampos processador = new ProcessadorDeCampos(contexto);
			processador.processar(emQualificador, objetoPreguicoso);
			return new CampoReferencia(campo, qualificador.valor(), qualificador);
		}

		@Override
		public String lista(Deque<Parte> emQualificador, Qualificadores contexto, Class<?> klass)
				throws ProblemaDeCompilacaoException {
			Parte qualificador = emQualificador.pop();
			Objeto<?> objetoPreguicoso = new Objeto<>(klass,qualificador);
			contexto.put(qualificador.valor(), objetoPreguicoso);
			ProcessadorDeCampos processador = new ProcessadorDeCampos(contexto);
			processador.processar(emQualificador, objetoPreguicoso);
			return qualificador.valor();
		}
	},
	COMPOSTA_COM_CONSTRUTOR {
		@Override
		public Campo objeto(Deque<Parte> emQualificador, String campo, Objeto<?> objeto, Qualificadores contexto)
				throws ProblemaDeCompilacaoException {
			Parte qualificador = emQualificador.pop();
			Objeto<?> objetoPreguicoso = classeDoCampo(campo, objeto.getKlasse(), emQualificador, contexto,
					qualificador);
			ProcessadorDeConstrutor processadorMaiorMenor = new ProcessadorDeConstrutor(objetoPreguicoso);
			ProcessadorDeCampos processadorDeColchetes = new ProcessadorDeCampos(contexto);
			processadorMaiorMenor.processar(emQualificador);
			processadorDeColchetes.processar(emQualificador, objetoPreguicoso);
			return new CampoReferencia(campo, qualificador.valor(), qualificador);
		}
	},
	LISTA {
		@Override
		public Campo objeto(Deque<Parte> emClasse, String campo, Objeto<?> objeto, Qualificadores contexto)
				throws ProblemaDeCompilacaoException {
			Parte classe = emClasse.pop();
			ProcessadorDeLista processador = new ProcessadorDeLista(classe.valor(), contexto, classe);
			processador.processar(emClasse);
			return new CampoReferenciaLista(campo, processador.getEstrategia(), classe);
		}
	};

	final public static Atribuicoes deAtributo(String value, String proximo) {
		if ("#".equals(value))
			return REFERENCIA;
		else if ("[".equals(proximo))
			return COMPOSTA;
		else if ("<".equals(proximo))
			return COMPOSTA_COM_CONSTRUTOR;
		else if ("{".equals(proximo))
			return LISTA;
		else
			return VALOR;
	}

	public abstract Campo objeto(Deque<Parte> emCampo, String campo, Objeto<?> objeto, Qualificadores contexto)
			throws ProblemaDeCompilacaoException;

	public static Atribuicoes deLista(String value, String proximo) {
		if ("#".equals(value))
			return REFERENCIA;
		else if ("[".equals(proximo))
			return COMPOSTA;
		else if ("<".equals(proximo))
			return COMPOSTA_COM_CONSTRUTOR;
		return INVALIDA;
	}

	public String lista(Deque<Parte> emInicioReferenciaOuObjeto, Qualificadores contexto, Class<?> klass)
			throws ProblemaDeCompilacaoException {
		throw new ProblemaDeCompilacaoException(null, emInicioReferenciaOuObjeto.peek());
	}

	Objeto<?> classeDoCampo(String campo, Class<?> klass, Deque<Parte> depoisQualificador, Qualificadores contexto,
			Parte qualificador) throws ProblemaDeCompilacaoException {
		try {
			Objeto<?> objeto = new Objeto<>(Classes.getCampo(campo, klass).getType(), qualificador);
			contexto.put(qualificador.valor(), objeto);
			return objeto;
		} catch (NullPointerException semCampo) {
			throw new ProblemaDeCompilacaoException(semCampo, qualificador);
		}
	}
}