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

import net.douglashiura.picon.linguagem.Picon.Escolha;

public class TestePiconEscolha {

	@Test
	public void referencia() {
		assertEquals(Escolha.REFERENCIA, Escolha.qual("#", "referencia"));
	}

	@Test
	public void composto() {
		assertEquals(Escolha.COMPOSTO, Escolha.qual("entidade", "["));
	}
	
	@Test
	public void compostoComConstrutor() {
		assertEquals(Escolha.COMPOSTO, Escolha.qual("entidade", "<"));
	}

	@Test
	public void lista() {
		assertEquals(Escolha.LISTA, Escolha.qual("com.Entidade", "{"));
	}

	
	@Test
	public void valor() {
		assertEquals(Escolha.VALUE, Escolha.qual("nome", "douglas"));
	}

}
