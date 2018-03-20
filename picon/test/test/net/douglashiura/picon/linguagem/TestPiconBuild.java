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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayDeque;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.ProblemaDeCompilacao;
import net.douglashiura.picon.linguagem.Fragmentador;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Picon;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.ContextoPreguisoso;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;
import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.EntidadeComConstrutor;

public class TestPiconBuild {

	private Qualificadores qualificadores;

	@Before
	public void setUp() {
		qualificadores = new Qualificadores();
	}

	@Test
	public void processarEntidadeVazia() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		assertNull(qualificadores.get(""));
	}

	@Test
	public void processarEntidadeBeanVazia() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{UID[]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		assertNotNull(qualificadores.get("UID"));
	}

	@Test
	public void processarEntidadeNome() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{UID[nome=HIURA;]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ObjetoPreguicoso<Entidade> objeto = qualificadores.get("UID");
		assertEquals("HIURA", objeto.instanciar(null).getNome());
	}

	@Test
	public void processarEntidadeNomeIdade() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{UID[nome=HIURA;idade=11;]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ObjetoPreguicoso<Entidade> objeto = qualificadores.get("UID");
		Entidade instancia = objeto.instanciar(null);
		assertEquals("HIURA", instancia.getNome());
		assertEquals(new Integer(11), instancia.getIdade());

	}

	@Test
	public void processarEntidadeNomeIdadeNome() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{UID[nome=HIURA;idade=11;nome=HIURA;]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ObjetoPreguicoso<Entidade> objeto = qualificadores.get("UID");
		Entidade instancia = objeto.instanciar(null);
		assertEquals("HIURA", instancia.getNome());
		assertEquals(new Integer(11), instancia.getIdade());

	}

	@Test
	public void processarEntidadeNomeIdadeDuas() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{UID[nome=HIURA;idade=11;]UID2[nome=HIURA2;idade=12;]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ObjetoPreguicoso<Entidade> objeto = qualificadores.get("UID");
		Entidade instancia = objeto.instanciar(null);
		ObjetoPreguicoso<Entidade> objeto2 = qualificadores.get("UID2");
		Entidade instancia2 = objeto2.instanciar(null);
		assertEquals("HIURA", instancia.getNome());
		assertEquals(new Integer(11), instancia.getIdade());
		assertEquals("HIURA2", instancia2.getNome());
		assertEquals(new Integer(12), instancia2.getIdade());
	}

	@Test
	public void processarEntidadeNomeEntidadeObjectReferenciado() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{UID[nome=HIURA;entidade #UID;]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ObjetoPreguicoso<Entidade> objeto = qualificadores.get("UID");
		Entidade instancia = objeto.instanciar(null);
		assertEquals("HIURA", instancia.getNome());
		assertNotNull(instancia.getEntidade());
	}

	@Test
	public void processarEntidadeNomeEntidadeObjectVazia() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{UID[entidade UID3[]]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ObjetoPreguicoso<Entidade> objeto = qualificadores.get("UID");
		Entidade instancia = objeto.instanciar(null);
		assertNotNull(instancia.getEntidade());
	}

	@Test
	public void processarEntidadeNomeEntidadeObject() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{UID[entidade UID3[nome Douglas;]]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ObjetoPreguicoso<Entidade> objeto = qualificadores.get("UID");
		Entidade instancia = objeto.instanciar(null);
		assertEquals("Douglas", instancia.getEntidade().getNome());
	}

	@Test(expected = ProblemaDeCompilacao.class)
	public void processarEntidadeNomeErro() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade\n{\nUID[entidade UID3[\nnome3 Douglas;]]}";
		try {
			Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		} catch (ProblemaDeCompilacao e) {
			assertEquals(4, e.getToke().getLinha());
			assertEquals("]", e.getToke().valor());
			throw e;
		}

	}

	@Test
	public void processarEntidadeNomeStringComConstrutor() throws Exception {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{douglas<Doug>[]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ObjetoPreguicoso<EntidadeComConstrutor> objeto = qualificadores.get("douglas");
		EntidadeComConstrutor instancia = objeto.instanciar(null);
		assertNotNull(instancia);
		assertEquals("Doug", instancia.obterNome());
	}

	@Test
	public void processarEntidadeNomeStringComConstrutorNaoSeiMais() throws Exception {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{pedro[] douglas<Doug #pedro>[]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ContextoPreguisoso contexto = new ContextoPreguisoso(qualificadores);
		ObjetoPreguicoso<EntidadeComConstrutor> objeto = qualificadores.get("douglas");
		EntidadeComConstrutor douglas = objeto.instanciar(contexto);
		assertNotNull(douglas);
		assertEquals("Doug", douglas.obterNome());
		assertEquals(contexto.get("pedro"), douglas.obterPedro());
	}

	@Test
	public void processarEntidadeNomeStringComConstrutorNaoSeiMaisComMane() throws Exception {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{pedro[] douglas<Doug #pedro>[nome=Douglas] mane[pedro=#pedro]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ContextoPreguisoso contexto = new ContextoPreguisoso(qualificadores);
		ObjetoPreguicoso<EntidadeComConstrutor> objeto = qualificadores.get("douglas");
		EntidadeComConstrutor douglas = objeto.instanciar(contexto);
		assertNotNull(douglas);
		assertEquals("Douglas", douglas.obterNome());
		assertEquals(contexto.get("pedro"), douglas.obterPedro());
	}

	@Test
	public void processarEntidadeNomeStringComConstrutorNaoSeiMaisInvertido() throws Exception {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{pedro[] douglas<Doug #pedro>[]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ContextoPreguisoso contexto = new ContextoPreguisoso(qualificadores);
		ObjetoPreguicoso<EntidadeComConstrutor> objeto = qualificadores.get("douglas");
		EntidadeComConstrutor douglas = objeto.instanciar(contexto);
		assertNotNull(douglas);
		assertEquals("Doug", douglas.obterNome());
		assertEquals(contexto.get("pedro"), douglas.obterPedro());
	}

	@Test
	public void processarEntidadeNomeComConstrutor() throws Exception {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{ pedro[] \ndouglas<#pedro>[]}";
		Picon.construir(qualificadores, new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		ContextoPreguisoso contexto = new ContextoPreguisoso(qualificadores);
		ObjetoPreguicoso<EntidadeComConstrutor> objeto = qualificadores.get("douglas");
		EntidadeComConstrutor douglas = objeto.instanciar(contexto);
		Object pedro = contexto.get("pedro");
		assertNotNull(douglas);
		assertEquals(pedro, douglas.obterPedro());
	}
}