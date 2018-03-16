/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/doug
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package test.net.douglashiura.picon;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.douglashiura.picon.Fragmentador;
import net.douglashiura.picon.Parte;

public class TesteTokenizador extends Assert {

	@Test
	public void vazio() {
		assertEquals(0, new Fragmentador("").getTokens().size());
	}

	@Test
	public void essesTokesEstranhos() {
		List<Parte> tokens = new Fragmentador("pedro[]\ndouglas<#pedro>[]").getTokens();
		assertEquals(10, tokens.size());

	}

	@Test
	public void qualificadorComConstrutor() {
		List<Parte> tokens = new Fragmentador("entidade<#referencia>").getTokens();
		assertEquals(5, tokens.size());
		assertEquals("entidade", tokens.get(0).valor());
		assertEquals("<", tokens.get(1).valor());
		assertEquals("#", tokens.get(2).valor());
		assertEquals("referencia", tokens.get(3).valor());
		assertEquals(">", tokens.get(4).valor());
	}

	@Test
	public void qualificadorComConstrutorComDuasReferencias() {
		List<Parte> tokens = new Fragmentador("entidade<#referencia #douglas>").getTokens();
		assertEquals(7, tokens.size());
		assertEquals("entidade", tokens.get(0).valor());
		assertEquals("<", tokens.get(1).valor());
		assertEquals("#", tokens.get(2).valor());
		assertEquals("referencia", tokens.get(3).valor());
		assertEquals("#", tokens.get(4).valor());
		assertEquals("douglas", tokens.get(5).valor());
		assertEquals(">", tokens.get(6).valor());

	}

	//
	@Test
	public void lixo() {
		assertEquals(0, new Fragmentador(" ").getTokens().size());
	}

	@Test
	public void toke() {
		assertEquals(1, new Fragmentador("toke").getTokens().size());
		assertNull(new Fragmentador("toke").getTokens().get(0).getAnterior());
	}

	@Test
	public void reservado() {
		assertEquals(1, new Fragmentador("{").getTokens().size());
		assertNull(new Fragmentador("{").getTokens().get(0).getAnterior());
	}

	//

	@Test
	public void lixolixo() {
		assertEquals(0, new Fragmentador(" 	").getTokens().size());
	}

	@Test
	public void reservadoReservado() {
		List<Parte> tokens;
		assertEquals(2, (tokens = new Fragmentador("{{").getTokens()).size());
		assertNotNull(tokens.get(1).getAnterior());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
	}

	@Test
	public void tokeToke() {
		List<Parte> tokens;
		assertEquals(2, (tokens = new Fragmentador("toke toke").getTokens()).size());
		assertNotNull(tokens.get(1).getAnterior());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
	}

	//
	@Test
	public void lixoReservado() {
		assertEquals(1, new Fragmentador(" {").getTokens().size());
	}

	@Test
	public void reservadoLixo() {
		assertEquals(1, new Fragmentador("{ ").getTokens().size());
	}

	@Test
	public void lixoToke() {
		assertEquals(1, new Fragmentador(" toke").getTokens().size());
	}

	@Test
	public void tokeLixo() {
		assertEquals(1, new Fragmentador("toke ").getTokens().size());
	}

	@Test
	public void reservadoToke() {
		List<Parte> tokens;
		assertEquals(2, (tokens = new Fragmentador("{toke").getTokens()).size());
		assertNotNull(tokens.get(1).getAnterior());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
	}

	@Test
	public void tokeReservado() {
		List<Parte> tokens;
		assertEquals(2, (tokens = new Fragmentador("toke{").getTokens()).size());
		assertNotNull(tokens.get(1).getAnterior());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
	}

	//
	@Test
	public void lixoReservadoLixo() {
		assertEquals(1, new Fragmentador(" { ").getTokens().size());
	}

	@Test
	public void lixoTokeLixo() {
		assertEquals(1, new Fragmentador(" toke ").getTokens().size());
	}

	@Test
	public void tokeLixoToke() {
		assertEquals(2, new Fragmentador("toke toke").getTokens().size());
	}

	@Test
	public void tokeReservadoToke() {
		List<Parte> tokens;
		assertEquals(3, (tokens = new Fragmentador("toke{toke").getTokens()).size());
		assertNotNull(tokens.get(1).getAnterior());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
		assertEquals(tokens.get(1), tokens.get(2).getAnterior());
	}

	@Test
	public void reservadoTokeReservado() {
		assertEquals(3, new Fragmentador("{toke{").getTokens().size());
	}

	@Test
	public void reservadoLixoReservado() {
		assertEquals(2, new Fragmentador("{ {").getTokens().size());
	}

	@Test
	public void pulandoVazio() {
		assertEquals(1, new Fragmentador("''").getTokens().size());
	}

	@Test
	public void pulandoA() {
		assertEquals(1, new Fragmentador("'a'").getTokens().size());
	}

	@Test
	public void pulandoABC() {
		assertEquals(1, new Fragmentador("'abcd'").getTokens().size());
	}

	@Test
	public void pulandoAgua_Viva() {
		assertEquals(1, new Fragmentador("'Agua Viva'").getTokens().size());
	}

	@Test
	public void pulandoAgua_RESERVADO_Viva() {
		assertEquals(1, new Fragmentador("'Agua#Viva'").getTokens().size());
	}

	@Test
	public void pulandoPulando() {
		assertEquals(2, new Fragmentador("''''").getTokens().size());
	}

	@Test
	public void pulandoToke() {
		assertEquals(2, new Fragmentador("''toke").getTokens().size());
	}

	@Test
	public void pulandoReservado() {
		assertEquals(2, new Fragmentador("''#").getTokens().size());
	}

	@Test
	public void pulandoLixo() {
		assertEquals(1, new Fragmentador("'' ").getTokens().size());
	}

	@Test
	public void lixoPulando() {
		assertEquals(1, new Fragmentador(" ''").getTokens().size());
	}

	@Test
	public void reservadoPulando() {
		assertEquals(2, new Fragmentador("#''").getTokens().size());
	}

	@Test
	public void tokePulando() {
		assertEquals(2, new Fragmentador("toke''").getTokens().size());
	}

	@Test
	public void tokeLixoPulando() {
		assertEquals(2, new Fragmentador("toke ''").getTokens().size());
	}

	@Test
	public void tokeReferenciaPulando() {
		assertEquals(3, new Fragmentador("toke#''").getTokens().size());
	}

	@Test
	public void tokePulandoToke() {
		assertEquals(3, new Fragmentador("toke''toke").getTokens().size());
	}

	@Test
	public void PulandoTokepulandoLixoReferenciaTokePulando() {
		List<Parte> tokens = new Fragmentador("toke='toke skip toke' toke '' #toke '#toke' ").getTokens();
		assertEquals(7, tokens.size());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
		assertEquals(tokens.get(1), tokens.get(2).getAnterior());
		assertEquals(tokens.get(2), tokens.get(3).getAnterior());
		assertEquals(tokens.get(4), tokens.get(5).getAnterior());
		assertEquals(tokens.get(5), tokens.get(6).getAnterior());
		assertEquals(1, tokens.get(1).getLinha());
		assertEquals(1, tokens.get(2).getLinha());
		assertEquals(1, tokens.get(3).getLinha());
		assertEquals(1, tokens.get(4).getLinha());
		assertEquals(1, tokens.get(5).getLinha());
		assertEquals(1, tokens.get(6).getLinha());
	}

	@Test
	public void quebraLinhaQuebraLinhaTokeTokeReservadorqQuebraLinhatokeToke() {
		List<Parte> tokens;
		assertEquals(5, (tokens = new Fragmentador("\n\ntoke toke{\ntoke\n\n\ntoke").getTokens()).size());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
		assertEquals(tokens.get(1), tokens.get(2).getAnterior());
		assertEquals(tokens.get(2), tokens.get(3).getAnterior());
		assertEquals(3, tokens.get(0).getLinha());
		assertEquals(3, tokens.get(1).getLinha());
		assertEquals(3, tokens.get(2).getLinha());
		assertEquals(4, tokens.get(3).getLinha());
		assertEquals(7, tokens.get(4).getLinha());
	}

	@Test
	public void quebraLinhaPulandoEmQuebraLinhaToke() {
		List<Parte> tokens;
		assertEquals(2, (tokens = new Fragmentador("\n'\n\n\n'toke").getTokens()).size());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
		assertEquals(5, tokens.get(0).getLinha());
		assertEquals(5, tokens.get(1).getLinha());

	}

}
