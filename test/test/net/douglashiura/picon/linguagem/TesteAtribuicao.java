// Douglas Hiura Longo, 21 março de 2018.

package test.net.douglashiura.picon.linguagem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.linguagem.Fragmentador;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.linguagem.atribuicao.Atribuicoes;
import net.douglashiura.picon.preguicoso.CampoPreguisoso;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;
import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.EntidadeComConstrutor;

public class TesteAtribuicao {
	private Qualificadores contexto;
	private ObjetoPreguicoso<?> umObjeto;

	@Before
	public void setUp() {
		contexto = new Qualificadores();
		umObjeto = new ObjetoPreguicoso<>(Entidade.class);
	}

	@Test
	public void referencia() {
		assertEquals(Atribuicoes.REFERENCIA, Atribuicoes.deAtributo("#", "referencia"));
	}

	@Test
	public void composto() {
		assertEquals(Atribuicoes.COMPOSTA, Atribuicoes.deAtributo("entidade", "["));
	}

	@Test
	public void compostoComConstrutor() {
		assertEquals(Atribuicoes.COMPOSTA_COM_CONSTRUTOR, Atribuicoes.deAtributo("entidade", "<"));
	}

	@Test
	public void lista() {
		assertEquals(Atribuicoes.LISTA, Atribuicoes.deAtributo("com.Entidade", "{"));
	}

	@Test
	public void valor() {
		assertEquals(Atribuicoes.VALOR, Atribuicoes.deAtributo("nome", "douglas"));
	}

	@Test
	public void listaProcessar() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{}]";
		Deque<Parte> emQualificador = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		CampoPreguisoso campo = Atribuicoes.LISTA.processar(emQualificador, "entidades", umObjeto, contexto);
		assertEquals("]", emQualificador.pop().valor());
		assertEquals("entidades", campo.getCampo());
		assertEquals("", campo.getValor());
	}

	@Test
	public void valorProcessar() throws Exception {
		String texto = "=10]";
		Deque<Parte> emQualificador = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		CampoPreguisoso campo = Atribuicoes.VALOR.processar(emQualificador, "idade", umObjeto, contexto);
		assertEquals("]", emQualificador.pop().valor());
		assertEquals("idade", campo.getCampo());
		assertEquals("10", campo.getValor());
	}

	@Test
	public void compostoComConstrutorProcessar() throws Exception {
		umObjeto = new ObjetoPreguicoso<>(EntidadeComConstrutor.class);
		String texto = "francisco<>[]]";
		Deque<Parte> emQualificador = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		CampoPreguisoso campo = Atribuicoes.COMPOSTA_COM_CONSTRUTOR.processar(emQualificador, "pedro", umObjeto, contexto);
		ObjetoPreguicoso<?> francisco = contexto.get("francisco");
		assertEquals("]", emQualificador.pop().valor());
		assertEquals("pedro", campo.getCampo());
		assertEquals("francisco", campo.getValor());
		assertEquals(0, francisco.getCampos().size());
		assertEquals(0, francisco.getParametros().size());
	}

	@Test
	public void referenciaProcessar() throws Exception {
		String texto = "#pedro]";
		Deque<Parte> emCadeia = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		CampoPreguisoso campo = Atribuicoes.REFERENCIA.processar(emCadeia, "entidade", umObjeto, contexto);
		assertEquals("]", emCadeia.pop().valor());
		assertEquals("entidade", campo.getCampo());
		assertEquals("pedro", campo.getValor());
	}

	@Test
	public void compostoProcessar() throws Exception {
		String texto = "pedro[]]";
		Deque<Parte> emQualificador = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		CampoPreguisoso campo = Atribuicoes.COMPOSTA.processar(emQualificador, "entidade", umObjeto, contexto);
		assertEquals("]", emQualificador.pop().valor());
		assertEquals("entidade", campo.getCampo());
		assertEquals("pedro", campo.getValor());
		assertNotNull(contexto.get("pedro"));
	}
}
