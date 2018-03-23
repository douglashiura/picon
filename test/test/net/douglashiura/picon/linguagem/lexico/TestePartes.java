/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/doug
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package test.net.douglashiura.picon.linguagem.lexico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Deque;

import org.junit.Test;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Partes;

public class TestePartes {

	@Test
	public void vazio() {
		assertEquals(0, Partes.explodir("").size());
	}

	@Test
	public void essesTokesEstranhos() {
		Deque<Parte> tokens = Partes.explodir("pedro[]\ndouglas<#pedro>[]");
		assertEquals(10, tokens.size());
	}

	@Test
	public void qualificadorComConstrutor() {
		Deque<Parte> tokens = Partes.explodir("entidade<#referencia>");
		Parte parte0 = tokens.pop();
		Parte parte1 = tokens.pop();
		Parte parte2 = tokens.pop();
		Parte parte3 = tokens.pop();
		Parte parte4 = tokens.pop();
		assertEquals(0, tokens.size());
		assertEquals("entidade", parte0.valor());
		assertEquals("<", parte1.valor());
		assertEquals("#", parte2.valor());
		assertEquals("referencia", parte3.valor());
		assertEquals(">", parte4.valor());
	}

	@Test
	public void qualificadorComConstrutorComDuasReferencias() {
		Deque<Parte> tokens = Partes.explodir("entidade<#referencia #douglas>");
		Parte parte0 = tokens.pop();
		Parte parte1 = tokens.pop();
		Parte parte2 = tokens.pop();
		Parte parte3 = tokens.pop();
		Parte parte4 = tokens.pop();
		Parte parte5 = tokens.pop();
		Parte parte6 = tokens.pop();
		assertEquals(0, tokens.size());
		assertEquals("entidade", parte0.valor());
		assertEquals("<", parte1.valor());
		assertEquals("#", parte2.valor());
		assertEquals("referencia", parte3.valor());
		assertEquals("#", parte4.valor());
		assertEquals("douglas", parte5.valor());
		assertEquals(">", parte6.valor());
	}

	@Test
	public void lixo() {
		assertEquals(0, Partes.explodir(" ").size());
	}

	@Test
	public void toke() {
		assertEquals(1, Partes.explodir("toke").size());
		assertNull(Partes.explodir("toke").pop().getAnterior());
	}

	@Test
	public void reservado() {
		assertEquals(1, Partes.explodir("{").size());
		assertNull(Partes.explodir("{").pop().getAnterior());
	}

	@Test
	public void lixolixo() {
		assertEquals(0, Partes.explodir(" 	").size());
	}

	@Test
	public void reservadoReservado() {
		Deque<Parte> tokens = Partes.explodir("{{");
		Parte parte0 = tokens.pop();
		Parte parte1 = tokens.pop();
		assertEquals(0, tokens.size());
		assertNotNull(parte1.getAnterior());
		assertEquals(parte0, parte1.getAnterior());
	}

	@Test
	public void tokeToke() {
		Deque<Parte> tokens = Partes.explodir("toke toke");
		Parte parte0 = tokens.pop();
		Parte parte1 = tokens.pop();
		assertEquals(0, tokens.size());
		assertNotNull(parte1.getAnterior());
		assertEquals(parte0, parte1.getAnterior());
	}

	@Test
	public void lixoReservado() {
		assertEquals(1, Partes.explodir(" {").size());
	}

	@Test
	public void reservadoLixo() {
		assertEquals(1, Partes.explodir("{ ").size());
	}

	@Test
	public void lixoToke() {
		assertEquals(1, Partes.explodir(" toke").size());
	}

	@Test
	public void tokeLixo() {
		assertEquals(1, Partes.explodir("toke ").size());
	}

	@Test
	public void reservadoToke() {
		Deque<Parte> tokens = Partes.explodir("{toke");
		Parte parte0 = tokens.pop();
		Parte parte1 = tokens.pop();
		assertEquals(0, tokens.size());
		assertNotNull(parte1.getAnterior());
		assertEquals(parte0, parte1.getAnterior());
	}

	@Test
	public void tokeReservado() {
		Deque<Parte> tokens = Partes.explodir("toke{");
		Parte parte0 = tokens.pop();
		Parte parte1 = tokens.pop();
		assertEquals(0, tokens.size());
		assertNotNull(parte1.getAnterior());
		assertEquals(parte0, parte1.getAnterior());
	}

	@Test
	public void lixoReservadoLixo() {
		assertEquals(1, Partes.explodir(" { ").size());
	}

	@Test
	public void lixoTokeLixo() {
		assertEquals(1, Partes.explodir(" toke ").size());
	}

	@Test
	public void tokeLixoToke() {
		assertEquals(2, Partes.explodir("toke toke").size());
	}

	@Test
	public void tokeReservadoToke() {
		Deque<Parte> tokens = Partes.explodir("toke{toke");
		Parte parte0 = tokens.pop();
		Parte parte1 = tokens.pop();
		Parte parte2 = tokens.pop();
		assertEquals(0, tokens.size());
		assertNotNull(parte1.getAnterior());
		assertEquals(parte0, parte1.getAnterior());
		assertEquals(parte1, parte2.getAnterior());
	}

	@Test
	public void reservadoTokeReservado() {
		assertEquals(3, Partes.explodir("{toke{").size());
	}

	@Test
	public void reservadoLixoReservado() {
		assertEquals(2, Partes.explodir("{ {").size());
	}

	@Test
	public void pulandoVazio() {
		assertEquals(1, Partes.explodir("''").size());
	}

	@Test
	public void pulandoA() {
		assertEquals(1, Partes.explodir("'a'").size());
	}

	@Test
	public void pulandoABC() {
		assertEquals(1, Partes.explodir("'abcd'").size());
	}

	@Test
	public void pulandoAgua_Viva() {
		assertEquals(1, Partes.explodir("'Agua Viva'").size());
	}

	@Test
	public void pulandoAgua_RESERVADO_Viva() {
		assertEquals(1, Partes.explodir("'Agua#Viva'").size());
	}

	@Test
	public void pulandoPulando() {
		assertEquals(2, Partes.explodir("''''").size());
	}

	@Test
	public void pulandoToke() {
		assertEquals(2, Partes.explodir("''toke").size());
	}

	@Test
	public void pulandoReservado() {
		assertEquals(2, Partes.explodir("''#").size());
	}

	@Test
	public void pulandoLixo() {
		assertEquals(1, Partes.explodir("'' ").size());
	}

	@Test
	public void lixoPulando() {
		assertEquals(1, Partes.explodir(" ''").size());
	}

	@Test
	public void reservadoPulando() {
		assertEquals(2, Partes.explodir("#''").size());
	}

	@Test
	public void tokePulando() {
		assertEquals(2, Partes.explodir("toke''").size());
	}

	@Test
	public void tokeLixoPulando() {
		assertEquals(2, Partes.explodir("toke ''").size());
	}

	@Test
	public void tokeReferenciaPulando() {
		assertEquals(3, Partes.explodir("toke#''").size());
	}

	@Test
	public void tokePulandoToke() {
		assertEquals(3, Partes.explodir("toke''toke").size());
	}

	@Test
	public void pulandoTokepulandoLixoReferenciaTokePulando() {
		Deque<Parte> tokens = Partes.explodir("toke='toke skip toke' toke '' #toke '#toke' ");
		Parte parte0 = tokens.pop();
		Parte parte1 = tokens.pop();
		Parte parte2 = tokens.pop();
		Parte parte3 = tokens.pop();
		Parte parte4 = tokens.pop();
		Parte parte5 = tokens.pop();
		Parte parte6 = tokens.pop();
		assertEquals(0, tokens.size());
		assertEquals(parte0, parte1.getAnterior());
		assertEquals(parte1, parte2.getAnterior());
		assertEquals(parte2, parte3.getAnterior());
		assertEquals(parte4, parte5.getAnterior());
		assertEquals(parte5, parte6.getAnterior());
		assertEquals(1, parte1.getLinha());
		assertEquals(1, parte2.getLinha());
		assertEquals(1, parte3.getLinha());
		assertEquals(1, parte4.getLinha());
		assertEquals(1, parte5.getLinha());
		assertEquals(1, parte6.getLinha());
	}

	@Test
	public void quebraLinhaQuebraLinhaTokeTokeReservadorqQuebraLinhatokeToke() {
		Deque<Parte> tokens = Partes.explodir("\n\ntoke toke{\ntoke\n\n\ntoke");
		Parte parte0 = tokens.pop();
		Parte parte1 = tokens.pop();
		Parte parte2 = tokens.pop();
		Parte parte3 = tokens.pop();
		Parte parte4 = tokens.pop();
		assertEquals(0, tokens.size());
		assertEquals(parte0, parte1.getAnterior());
		assertEquals(parte1, parte2.getAnterior());
		assertEquals(parte2, parte3.getAnterior());
		assertEquals(3, parte0.getLinha());
		assertEquals(3, parte1.getLinha());
		assertEquals(3, parte2.getLinha());
		assertEquals(4, parte3.getLinha());
		assertEquals(7, parte4.getLinha());
	}

	@Test
	public void quebraLinhaPulandoEmQuebraLinhaToke() {
		Deque<Parte> tokens = Partes.explodir("\n'\n\n\n'toke");
		Parte parte0 = tokens.pop();
		Parte parte1 = tokens.pop();
		assertEquals(0, tokens.size());
		assertEquals(parte0, parte1.getAnterior());
		assertEquals(5, parte0.getLinha());
		assertEquals(5, parte1.getLinha());

	}

}
