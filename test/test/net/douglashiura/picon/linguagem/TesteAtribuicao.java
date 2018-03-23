// Douglas Hiura Longo, 21 março de 2018.

package test.net.douglashiura.picon.linguagem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Deque;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Partes;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.linguagem.atribuicao.Atribuicoes;
import net.douglashiura.picon.preguicoso.Campo;
import net.douglashiura.picon.preguicoso.Objeto;
import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.EntidadeComConstrutor;

public class TesteAtribuicao {
	private Qualificadores indentificacoes;
	private Objeto<?> umObjeto;

	@Before
	public void setUp() {
		indentificacoes = new Qualificadores();
		umObjeto = new Objeto<>(Entidade.class);
	}

	@Test
	public void referenciaDeElementoDeLista() {
		assertEquals(Atribuicoes.REFERENCIA, Atribuicoes.deElementoDeLista("#", "referencia"));
	}

	@Test
	public void compostoDeElementoDeLista() {
		assertEquals(Atribuicoes.COMPOSTA, Atribuicoes.deElementoDeLista("entidade", "["));
	}

	@Test
	public void compostoComConstrutorDeElementoDeLista() {
		assertEquals(Atribuicoes.COMPOSTA_COM_CONSTRUTOR, Atribuicoes.deElementoDeLista("entidade", "<"));
	}

	@Test
	public void invalidaComConstrutorDeElementoDeLista() {
		assertEquals(Atribuicoes.INVALIDA, Atribuicoes.deElementoDeLista("entidade", "Douglas"));
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
		Deque<Parte> emQualificador = Partes.explodir(texto);
		Campo campo = Atribuicoes.LISTA.processarDoObjeto(emQualificador, "entidades", umObjeto, indentificacoes);
		assertEquals("]", emQualificador.pop().valor());
		assertEquals("entidades", campo.getCampo());
		assertEquals("[]", campo.getValor());
	}

	@Test
	public void valorProcessar() throws Exception {
		String texto = "=10]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		Campo campo = Atribuicoes.VALOR.processarDoObjeto(emQualificador, "idade", umObjeto, indentificacoes);
		assertEquals("]", emQualificador.pop().valor());
		assertEquals("idade", campo.getCampo());
		assertEquals("10", campo.getValor());
	}

	@Test
	public void compostoComConstrutorProcessar() throws Exception {
		umObjeto = new Objeto<>(EntidadeComConstrutor.class);
		String texto = "francisco<>[]]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		Campo campo = Atribuicoes.COMPOSTA_COM_CONSTRUTOR.processarDoObjeto(emQualificador, "pedro", umObjeto, indentificacoes);
		Objeto<?> francisco = indentificacoes.get("francisco");
		assertEquals("]", emQualificador.pop().valor());
		assertEquals("pedro", campo.getCampo());
		assertEquals("francisco", campo.getValor());
		assertEquals(0, francisco.getCampos().size());
		assertEquals(0, francisco.getParametros().size());
	}

	@Test
	public void referenciaProcessar() throws Exception {
		String texto = "#pedro]";
		Deque<Parte> emCadeia = Partes.explodir(texto);
		Campo campo = Atribuicoes.REFERENCIA.processarDoObjeto(emCadeia, "entidade", umObjeto, indentificacoes);
		assertEquals("]", emCadeia.pop().valor());
		assertEquals("entidade", campo.getCampo());
		assertEquals("pedro", campo.getValor());
	}

	@Test
	public void compostoProcessar() throws Exception {
		String texto = "pedro[]]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		Campo campo = Atribuicoes.COMPOSTA.processarDoObjeto(emQualificador, "entidade", umObjeto, indentificacoes);
		assertEquals("]", emQualificador.pop().valor());
		assertEquals("entidade", campo.getCampo());
		assertEquals("pedro", campo.getValor());
		assertNotNull(indentificacoes.get("pedro"));
	}
}
