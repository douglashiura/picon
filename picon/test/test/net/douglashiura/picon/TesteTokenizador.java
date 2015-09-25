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

import net.douglashiura.picon.Parte;
import net.douglashiura.picon.Fragmentador;

import org.junit.Assert;
import org.junit.Test;

public class TesteTokenizador extends Assert {

	@Test
	public void vazio() {
		assertEquals(0, new Fragmentador("").getTokes().size());
	}
	
	@Test
	public void essesTokesEstranhos() {
		List<Parte> tokens = new Fragmentador("pedro[]\ndouglas<#pedro>[]").getTokes();
		assertEquals(10, tokens.size());		
		
	}
	

			
			@Test
	public void qualificadorComConstrutor() {
		List<Parte> tokens = new Fragmentador("entidade<#referencia>").getTokes();
		assertEquals(5, tokens.size());
		assertEquals("entidade", tokens.get(0).value());
		assertEquals("<", tokens.get(1).value());
		assertEquals("#", tokens.get(2).value());
		assertEquals("referencia", tokens.get(3).value());
		assertEquals(">", tokens.get(4).value());
	}	

	@Test
	public void qualificadorComConstrutorComDuasReferencias() {
		List<Parte> tokens = new Fragmentador("entidade<#referencia #douglas>").getTokes();
		assertEquals(7, tokens.size());
		assertEquals("entidade", tokens.get(0).value());
		assertEquals("<", tokens.get(1).value());
		assertEquals("#", tokens.get(2).value());
		assertEquals("referencia", tokens.get(3).value());
		assertEquals("#", tokens.get(4).value());
		assertEquals("douglas", tokens.get(5).value());
		assertEquals(">", tokens.get(6).value());
		
		
	}	

	
	
	//
	@Test
	public void lixo() {
		assertEquals(0, new Fragmentador(" ").getTokes().size());
	}

	@Test
	public void toke() {
		assertEquals(1, new Fragmentador("toke").getTokes().size());
		assertNull(new Fragmentador("toke").getTokes().get(0).getAnterior());
	}

	@Test
	public void reservado() {
		assertEquals(1, new Fragmentador("{").getTokes().size());
		assertNull(new Fragmentador("{").getTokes().get(0).getAnterior());
	}

	//

	@Test
	public void lixolixo() {
		assertEquals(0, new Fragmentador(" 	").getTokes().size());
	}

	@Test
	public void reservadoReservado() {
		List<Parte> tokens;
		assertEquals(2, (tokens = new Fragmentador("{{").getTokes()).size());
		assertNotNull(tokens.get(1).getAnterior());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
	}

	@Test
	public void tokeToke() {
		List<Parte> tokens;
		assertEquals(2,
				(tokens = new Fragmentador("toke toke").getTokes()).size());
		assertNotNull(tokens.get(1).getAnterior());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
	}

	
	
	//
	@Test
	public void lixoReservado() {
		assertEquals(1, new Fragmentador(" {").getTokes().size());
	}

	@Test
	public void reservadoLixo() {
		assertEquals(1, new Fragmentador("{ ").getTokes().size());
	}

	@Test
	public void lixoToke() {
		assertEquals(1, new Fragmentador(" toke").getTokes().size());
	}

	@Test
	public void tokeLixo() {
		assertEquals(1, new Fragmentador("toke ").getTokes().size());
	}

	@Test
	public void reservadoToke() {
		List<Parte> tokens;
		assertEquals(2, (tokens = new Fragmentador("{toke").getTokes()).size());
		assertNotNull(tokens.get(1).getAnterior());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
	}

	@Test
	public void tokeReservado() {
		List<Parte> tokens;
		assertEquals(2, (tokens = new Fragmentador("toke{").getTokes()).size());
		assertNotNull(tokens.get(1).getAnterior());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
	}

	//
	@Test
	public void lixoReservadoLixo() {
		assertEquals(1, new Fragmentador(" { ").getTokes().size());
	}

	@Test
	public void lixoTokeLixo() {
		assertEquals(1, new Fragmentador(" toke ").getTokes().size());
	}

	@Test
	public void tokeLixoToke() {
		assertEquals(2, new Fragmentador("toke toke").getTokes().size());
	}

	@Test
	public void tokeReservadoToke() {
		List<Parte> tokens;
		assertEquals(3,
				(tokens = new Fragmentador("toke{toke").getTokes()).size());
		assertNotNull(tokens.get(1).getAnterior());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
		assertEquals(tokens.get(1), tokens.get(2).getAnterior());
	}

	@Test
	public void reservadoTokeReservado() {
		assertEquals(3, new Fragmentador("{toke{").getTokes().size());
	}

	@Test
	public void reservadoLixoReservado() {
		assertEquals(2, new Fragmentador("{ {").getTokes().size());
	}

	@Test
	public void pulandoVazio() {
		assertEquals(1, new Fragmentador("''").getTokes().size());
	}

	@Test
	public void pulandoA() {
		assertEquals(1, new Fragmentador("'a'").getTokes().size());
	}

	@Test
	public void pulandoABC() {
		assertEquals(1, new Fragmentador("'abcd'").getTokes().size());
	}

	@Test
	public void pulandoAgua_Viva() {
		assertEquals(1, new Fragmentador("'Agua Viva'").getTokes().size());
	}

	@Test
	public void pulandoAgua_RESERVADO_Viva() {
		assertEquals(1, new Fragmentador("'Agua#Viva'").getTokes().size());
	}

	@Test
	public void pulandoPulando() {
		assertEquals(2, new Fragmentador("''''").getTokes().size());
	}

	@Test
	public void pulandoToke() {
		assertEquals(2, new Fragmentador("''toke").getTokes().size());
	}

	@Test
	public void pulandoReservado() {
		assertEquals(2, new Fragmentador("''#").getTokes().size());
	}

	@Test
	public void pulandoLixo() {
		assertEquals(1, new Fragmentador("'' ").getTokes().size());
	}

	@Test
	public void lixoPulando() {
		assertEquals(1, new Fragmentador(" ''").getTokes().size());
	}

	@Test
	public void reservadoPulando() {
		assertEquals(2, new Fragmentador("#''").getTokes().size());
	}

	@Test
	public void tokePulando() {
		assertEquals(2, new Fragmentador("toke''").getTokes().size());
	}

	@Test
	public void tokeLixoPulando() {
		assertEquals(2, new Fragmentador("toke ''").getTokes().size());
	}

	@Test
	public void tokeReferenciaPulando() {
		assertEquals(3, new Fragmentador("toke#''").getTokes().size());
	}

	@Test
	public void tokePulandoToke() {
		assertEquals(3, new Fragmentador("toke''toke").getTokes().size());
	}

	@Test
	public void PulandoTokepulandoLixoReferenciaTokePulando() {
		List<Parte> tokens;
		assertEquals(
				7,
				(tokens = new Fragmentador(
						"toke='toke skip toke' toke '' #toke '#toke' ")
						.getTokes()).size());
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
		assertEquals(5, (tokens = new Fragmentador(
				"\n\ntoke toke{\ntoke\n\n\ntoke").getTokes()).size());
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
		assertEquals(2,
				(tokens = new Fragmentador("\n'\n\n\n'toke").getTokes()).size());
		assertEquals(tokens.get(0), tokens.get(1).getAnterior());
		assertEquals(5, tokens.get(0).getLinha());
		assertEquals(5, tokens.get(1).getLinha());

	}

}
