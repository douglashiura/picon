/*
 * Douglas Hiura Longo, 14 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package test.net.douglashiura.picon.linguagem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.douglashiura.picon.linguagem.Atribuicoes;

public class TesteEscolha {

	@Test
	public void referencia() {
		assertEquals(Atribuicoes.REFERENCIA, Atribuicoes.qual("#", "referencia"));
	}

	@Test
	public void composto() {
		assertEquals(Atribuicoes.COMPOSTA, Atribuicoes.qual("entidade", "["));
	}

	@Test
	public void compostoComConstrutor() {
		assertEquals(Atribuicoes.COMPOSTA_COM_CONSTRUTOR, Atribuicoes.qual("entidade", "<"));
	}

	@Test
	public void lista() {
		assertEquals(Atribuicoes.LISTA, Atribuicoes.qual("com.Entidade", "{"));
	}

	@Test
	public void valor() {
		assertEquals(Atribuicoes.VALUE, Atribuicoes.qual("nome", "douglas"));
	}

}
