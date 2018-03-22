// Douglas Hiura Longo, 12 de Setembro de 2010.
package net.douglashiura.picon.linguagem.atribuicao;

import java.lang.reflect.Field;
import java.util.Deque;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.CampoPreguisoso;
import net.douglashiura.picon.preguicoso.ClassePreguicosa;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;

public enum Atribuicoes {
	INVALIDA {

		@Override
		public CampoPreguisoso processar(Deque<Parte> emCampo, String campo, ObjetoPreguicoso<?> umObjeto,
				Qualificadores contexto) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
			throw new RuntimeException();
		}
	},
	REFERENCIA {
		@Override
		public CampoPreguisoso processar(Deque<Parte> emCadeia, String campo, ObjetoPreguicoso<?> umObjeto,
				Qualificadores contexto) {
			emCadeia.pop();
			return new CampoPreguisoso(campo, emCadeia.pop().valor());
		}

		@Override
		public String processarElementoDaLista(Deque<Parte> emCadeia, Qualificadores contexto, Class<?> klass) {
			emCadeia.pop();
			return emCadeia.pop().valor();

		}
	},
	VALOR {
		@Override
		public CampoPreguisoso processar(Deque<Parte> emValor, String campo, ObjetoPreguicoso<?> umObjeto,
				Qualificadores contexto) {
			return new CampoPreguisoso(campo, emValor.pop().valor());
		}
	},
	COMPOSTA {
		@Override
		public CampoPreguisoso processar(Deque<Parte> emQualificador, String campo, ObjetoPreguicoso<?> umObjeto,
				Qualificadores contexto) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
			Parte qualificador = emQualificador.pop();
			Field tipo = umObjeto.getKlasse().getDeclaredField(campo);
			ObjetoPreguicoso<?> objetoPreguicoso = new ClassePreguicosa<>(tipo.getType(), contexto)
					.registre(qualificador.valor());
			ProcessaAtribuicoes processadorDeColchetes = new ProcessaAtribuicoes(contexto);
			processadorDeColchetes.processar(emQualificador, objetoPreguicoso);
			return new CampoPreguisoso(campo, qualificador.valor());
		}

		@Override
		public String processarElementoDaLista(Deque<Parte> emQualificador, Qualificadores contexto, Class<?> klass)
				throws NoSuchFieldException, SecurityException, ClassNotFoundException {
			Parte qualificador = emQualificador.pop();
			ObjetoPreguicoso<?> objetoPreguicoso = new ClassePreguicosa<>(klass, contexto)
					.registre(qualificador.valor());
			ProcessaAtribuicoes processadorDeColchetes = new ProcessaAtribuicoes(contexto);
			processadorDeColchetes.processar(emQualificador, objetoPreguicoso);
			return qualificador.valor();
		}
	},
	COMPOSTA_COM_CONSTRUTOR {
		@Override
		public CampoPreguisoso processar(Deque<Parte> emQualificador, String campo, ObjetoPreguicoso<?> umObjeto,
				Qualificadores contexto) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
			Parte qualificador = emQualificador.pop();
			Field tipo = umObjeto.getKlasse().getDeclaredField(campo);
			ObjetoPreguicoso<?> objetoPreguicoso = new ClassePreguicosa<>(tipo.getType(), contexto)
					.registre(qualificador.valor());
			ProcessadorDeMaiorMenor processadorMaiorMenor = new ProcessadorDeMaiorMenor(objetoPreguicoso);
			ProcessaAtribuicoes processadorDeColchetes = new ProcessaAtribuicoes(contexto);
			processadorMaiorMenor.processar(emQualificador);
			processadorDeColchetes.processar(emQualificador, objetoPreguicoso);
			return new CampoPreguisoso(campo, qualificador.valor());
		}
	},
	LISTA {
		@Override
		public CampoPreguisoso processar(Deque<Parte> emClasse, String campo, ObjetoPreguicoso<?> umObjeto,
				Qualificadores contexto) throws ClassNotFoundException, NoSuchFieldException, SecurityException {
			Parte classe = emClasse.pop();
			ProcessadorDeLista processadorDeLista = new ProcessadorDeLista(classe.valor(), contexto);
			processadorDeLista.processar(emClasse);
			return new CampoPreguisoso(campo, processadorDeLista.getValores());
		}
	};

	final public static Atribuicoes deAtributo(String value, String proximo) {
		if ("#".equals(value))
			return REFERENCIA;
		else if ("[".equals(proximo))
			return COMPOSTA;
		else if ("<".equals(proximo))
			return Atribuicoes.COMPOSTA_COM_CONSTRUTOR;
		else if ("{".equals(proximo))
			return LISTA;
		else
			return VALOR;
	}

	public abstract CampoPreguisoso processar(Deque<Parte> emCampo, String campo, ObjetoPreguicoso<?> umObjeto,
			Qualificadores contexto) throws NoSuchFieldException, SecurityException, ClassNotFoundException;

	public static Atribuicoes deElementoDeLista(String value, String proximo) {
		if ("#".equals(value))
			return REFERENCIA;
		else if ("[".equals(proximo))
			return COMPOSTA;
		else if ("<".equals(proximo))
			return Atribuicoes.COMPOSTA_COM_CONSTRUTOR;
		return INVALIDA;
	}

	public String processarElementoDaLista(Deque<Parte> emInicioReferenciaOuObjeto, Qualificadores contexto,
			Class<?> klass) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		throw new RuntimeException();
	}
}