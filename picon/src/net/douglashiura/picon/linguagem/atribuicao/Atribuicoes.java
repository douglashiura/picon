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
	REFERENCIA {
		@Override
		public CampoPreguisoso processar(Deque<Parte> emCadeia, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) {
			emCadeia.pop();
			Parte qualificador = emCadeia.pop();
			return new CampoPreguisoso(campo, qualificador.valor());
		}
	},
	VALOR {
		@Override
		public CampoPreguisoso processar(Deque<Parte> emValor, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) {
			return new CampoPreguisoso(campo, emValor.pop().valor());
		}
	},
	COMPOSTA {
		@Override
		public CampoPreguisoso processar(Deque<Parte> emQualificador, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) throws NoSuchFieldException, SecurityException {
			Parte qualificador = emQualificador.pop();
			Field tipo = umObjeto.getKlasse().getDeclaredField(campo);
			ObjetoPreguicoso<?> objetoPreguicoso = new ClassePreguicosa<>(tipo.getType(), contexto).registre(qualificador.valor());
			ProcessadorDeColchetes processadorDeColchetes = new ProcessadorDeColchetes(objetoPreguicoso);
			processadorDeColchetes.processar(emQualificador);
			return new CampoPreguisoso(campo, qualificador.valor());
		}
	},
	COMPOSTA_COM_CONSTRUTOR {
		@Override
		public CampoPreguisoso processar(Deque<Parte> emQualificador, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) throws NoSuchFieldException, SecurityException {
			Parte qualificador = emQualificador.pop();
			Field tipo = umObjeto.getKlasse().getDeclaredField(campo);
			ObjetoPreguicoso<?> objetoPreguicoso = new ClassePreguicosa<>(tipo.getType(), contexto).registre(qualificador.valor());
			ProcessadorDeMaiorMenor processadorMaiorMenor = new ProcessadorDeMaiorMenor(objetoPreguicoso);
			ProcessadorDeColchetes processadorDeColchetes = new ProcessadorDeColchetes(objetoPreguicoso);
			processadorMaiorMenor.processar(emQualificador);
			processadorDeColchetes.processar(emQualificador);
			return new CampoPreguisoso(campo, qualificador.valor());
		}
	},
	LISTA {
		@Override
		public CampoPreguisoso processar(Deque<Parte> emClasse, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) throws ClassNotFoundException {
			Parte classe = emClasse.pop();
			ProcessadorDeLista processadorDeLista = new ProcessadorDeLista(classe.valor());
			processadorDeLista.processar(emClasse);
			return new CampoPreguisoso(campo, processadorDeLista.getValores());
		}
	};

	final public static Atribuicoes sincronizar(String value, String proximo) {
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

	public abstract CampoPreguisoso processar(Deque<Parte> emCampo, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) throws NoSuchFieldException, SecurityException, ClassNotFoundException;

	public void sincronizeValor(Deque<Parte> inicioDeColchetes, Parte valor) {
		inicioDeColchetes.push(valor);
	}
}