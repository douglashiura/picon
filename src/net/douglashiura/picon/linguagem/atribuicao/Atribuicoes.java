// Douglas Hiura Longo, 12 de Setembro de 2010.
package net.douglashiura.picon.linguagem.atribuicao;

import java.lang.reflect.Field;
import java.util.Deque;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.Campo;
import net.douglashiura.picon.preguicoso.CampoReferenciaLista;
import net.douglashiura.picon.preguicoso.CampoReferencia;
import net.douglashiura.picon.preguicoso.Objeto;

public enum Atribuicoes {
	INVALIDA {

		@Override
		public Campo processarDoObjeto(Deque<Parte> emCampo, String campo, Objeto<?> objeto, Qualificadores contexto) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
			throw new RuntimeException();
		}
	},
	REFERENCIA {
		@Override
		public Campo processarDoObjeto(Deque<Parte> emCadeia, String campo, Objeto<?> objeto, Qualificadores contexto) {
			emCadeia.pop();
			return new CampoReferencia(campo, emCadeia.pop().valor());
		}

		@Override
		public String processarElementoDaLista(Deque<Parte> emCadeia, Qualificadores contexto, Class<?> klass) {
			emCadeia.pop();
			return emCadeia.pop().valor();

		}
	},
	VALOR {
		@Override
		public Campo processarDoObjeto(Deque<Parte> emValor, String campo, Objeto<?> objeto, Qualificadores contexto) {
			return new Campo(campo, emValor.pop().valor());
		}
	},
	COMPOSTA {
		@Override
		public Campo processarDoObjeto(Deque<Parte> emQualificador, String campo, Objeto<?> objeto, Qualificadores contexto) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
			Parte qualificador = emQualificador.pop();
			Field tipo = objeto.getKlasse().getDeclaredField(campo);
			Objeto<?> objetoPreguicoso = new Objeto<>(tipo.getType());
			contexto.put(qualificador.valor(), objetoPreguicoso);
			ProcessaAtribuicoes processadorDeColchetes = new ProcessaAtribuicoes(contexto);
			processadorDeColchetes.processar(emQualificador, objetoPreguicoso);
			return new CampoReferencia(campo, qualificador.valor());
		}

		@Override
		public String processarElementoDaLista(Deque<Parte> emQualificador, Qualificadores contexto, Class<?> klass) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
			Parte qualificador = emQualificador.pop();
			Objeto<?> objetoPreguicoso = new Objeto<>(klass);
			contexto.put(qualificador.valor(), objetoPreguicoso);
			ProcessaAtribuicoes processadorDeColchetes = new ProcessaAtribuicoes(contexto);
			processadorDeColchetes.processar(emQualificador, objetoPreguicoso);
			return qualificador.valor();
		}
	},
	COMPOSTA_COM_CONSTRUTOR {
		@Override
		public Campo processarDoObjeto(Deque<Parte> emQualificador, String campo, Objeto<?> objeto, Qualificadores contexto) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
			Parte qualificador = emQualificador.pop();
			Field tipo = objeto.getKlasse().getDeclaredField(campo);
			Objeto<?> objetoPreguicoso = new Objeto<>(tipo.getType());
			contexto.put(qualificador.valor(), objetoPreguicoso);
			ProcessadorDeMaiorMenor processadorMaiorMenor = new ProcessadorDeMaiorMenor(objetoPreguicoso);
			ProcessaAtribuicoes processadorDeColchetes = new ProcessaAtribuicoes(contexto);
			processadorMaiorMenor.processar(emQualificador);
			processadorDeColchetes.processar(emQualificador, objetoPreguicoso);
			return new CampoReferencia(campo, qualificador.valor());
		}
	},
	LISTA {
		@Override
		public Campo processarDoObjeto(Deque<Parte> emClasse, String campo, Objeto<?> objeto, Qualificadores contexto) throws ClassNotFoundException, NoSuchFieldException, SecurityException {
			Parte classe = emClasse.pop();
			ProcessadorDeLista processadorDeLista = new ProcessadorDeLista(classe.valor(), contexto);
			processadorDeLista.processar(emClasse);
			return new CampoReferenciaLista(campo, processadorDeLista.getValores());
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

	public abstract Campo processarDoObjeto(Deque<Parte> emCampo, String campo, Objeto<?> objeto, Qualificadores contexto) throws NoSuchFieldException, SecurityException, ClassNotFoundException;

	public static Atribuicoes deElementoDeLista(String value, String proximo) {
		if ("#".equals(value))
			return REFERENCIA;
		else if ("[".equals(proximo))
			return COMPOSTA;
		else if ("<".equals(proximo))
			return COMPOSTA_COM_CONSTRUTOR;
		return INVALIDA;
	}

	public String processarElementoDaLista(Deque<Parte> emInicioReferenciaOuObjeto, Qualificadores contexto, Class<?> klass) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		throw new RuntimeException();
	}
}