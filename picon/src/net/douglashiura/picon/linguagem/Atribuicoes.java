package net.douglashiura.picon.linguagem;

import java.util.Deque;

import net.douglashiura.picon.preguicoso.CampoPreguisoso;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;

public enum Atribuicoes {
	REFERENCIA {
		@Override
		public	CampoPreguisoso processar(Deque<Parte> emQualificador, String cadeia, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) {
			return null;
		}
	},
	VALUE {
		@Override
		public	CampoPreguisoso processar(Deque<Parte> emCampo, String valor, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) {
			return new CampoPreguisoso(campo, valor);
		}
	},
	COMPOSTA {
		@Override
		public	CampoPreguisoso processar(Deque<Parte> emColchetes, String valor, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) {
			return null;
		}
	},
	LISTA {
		@Override
		public	CampoPreguisoso processar(Deque<Parte> emChave, String valor, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) {
			return null;
		}
	},
	COMPOSTA_COM_CONSTRUTOR {
		@Override
		public	CampoPreguisoso processar(Deque<Parte> emMenor, String valor, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto) {
			return null;
		}
	};
	final public static Atribuicoes qual(String value, String proximo) {
		if ("#".equals(value))
			return REFERENCIA;
		else if ("[".equals(proximo))
			return COMPOSTA;
		else if ("<".equals(proximo))
			return Atribuicoes.COMPOSTA_COM_CONSTRUTOR;
		else if ("{".equals(proximo))
			return LISTA;
		else
			return VALUE;
	}

	public abstract CampoPreguisoso processar(Deque<Parte> emCampo, String valor, String campo, ObjetoPreguicoso<?> umObjeto, Qualificadores contexto);
}