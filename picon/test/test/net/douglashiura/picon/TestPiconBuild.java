/*
 * Douglas Hiura Longo, 14 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package test.net.douglashiura.picon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayDeque;

import net.douglashiura.picon.ProblemaDeCompilacao;
import net.douglashiura.picon.Picon;
import net.douglashiura.picon.PiconStore;
import net.douglashiura.picon.Parte;
import net.douglashiura.picon.Fragmentador;

import org.junit.Assert;
import org.junit.Test;

public class TestPiconBuild {

	@Test
	public void processarEntidadeVazia() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals(0, picon.getStore().size());
	}

	@Test
	public void processarEntidadeBeanVazia() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{UID[]}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals(1, picon.getStore().size());
	}

	@Test
	public void processarEntidadeNome() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{UID[nome=HIURA;]}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals("HIURA", picon.get("UID", Entidade.class).getNome());

	}

	@Test
	public void processarEntidadeNomeIdade() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{UID[nome=HIURA;idade=11;]}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals("HIURA", picon.get("UID", Entidade.class).getNome());
		Assert.assertEquals(new Integer(11), picon.get("UID", Entidade.class).getIdade());

	}

	@Test
	public void processarEntidadeNomeIdadeNome() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{UID[nome=HIURA;idade=11;nome=HIURA;]}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals("HIURA", picon.get("UID", Entidade.class).getNome());
		Assert.assertEquals(new Integer(11), picon.get("UID", Entidade.class).getIdade());

	}

	@Test
	public void processarEntidadeNomeIdadeDuas() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{UID[nome=HIURA;idade=11;]UID2[nome=HIURA;idade=11;]}";

		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals("HIURA", picon.get("UID", Entidade.class).getNome());
		Assert.assertEquals(new Integer(11), picon.get("UID", Entidade.class).getIdade());

	}

	@Test
	public void processarEntidadeNomeEntidadeObjectReferenciado() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{UID[nome=HIURA;entidade #UID;]}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals("HIURA", picon.get("UID", Entidade.class).getNome());
		Assert.assertNotNull(picon.get("UID", Entidade.class).getEntidade());
	}

	@Test
	public void processarEntidadeNomeEntidadeObjectVazia() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{UID[entidade UID3[]]}";

		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals(1, picon.getStore().size());
		Assert.assertNotNull(picon.get("UID", Entidade.class).getEntidade());
	}

	@Test
	public void processarEntidadeNomeEntidadeObject() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade{UID[entidade UID3[nome Douglas;]]}";

		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals(1, picon.getStore().size());
		Assert.assertNotNull(picon.get("UID", Entidade.class).getEntidade());
		Assert.assertEquals("Douglas", picon.get("UID", Entidade.class).getEntidade().getNome());
	}

	@Test(expected = ProblemaDeCompilacao.class)
	public void processarEntidadeNomeErro() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.Entidade\n{\nUID[entidade UID3[\nnome3 Douglas;]]}";
		try {
			Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		} catch (ProblemaDeCompilacao e) {
			Assert.assertEquals(4, e.getToke().getLinha());
			Assert.assertEquals("]", e.getToke().valor());
			throw e;
		}

	}

	@Test
	public void processarEntidadeNomeStringComConstrutor() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{douglas<Doug>[]}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals(1, picon.getStore().size());
		EntidadeComConstrutor douglas = picon.get("douglas");
		Assert.assertNotNull(douglas);
		assertEquals("Doug", douglas.obterNome());
	}

	@Test
	public void processarEntidadeNomeStringComConstrutorNaoSeiMais() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{pedro[] douglas<Doug #pedro>[]}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals(2, picon.getStore().size());
		EntidadeComConstrutor douglas = picon.get("douglas");
		assertNotNull(douglas);
		assertEquals("Doug", douglas.obterNome());
		assertEquals(picon.get("pedro"), douglas.obterPedro());
	}
	
	@Test
	public void processarEntidadeNomeStringComConstrutorNaoSeiMaisComMane() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{pedro[] douglas<Doug #pedro>[nome=Douglas] mane[pedro=#pedro]}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals(3, picon.getStore().size());
		EntidadeComConstrutor douglas = picon.get("douglas");
		assertNotNull(douglas);
		assertEquals("Douglas", douglas.obterNome());
		assertEquals(picon.get("pedro"), douglas.obterPedro());
	}

	@Test
	public void processarEntidadeNomeStringComConstrutorNaoSeiMaisInvertido() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{pedro[] douglas<Doug #pedro>[]}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		assertEquals(2, picon.getStore().size());
		EntidadeComConstrutor douglas = picon.get("douglas");
		assertNotNull(douglas);
		assertEquals("Doug", douglas.obterNome());
		assertEquals(picon.get("pedro"), douglas.obterPedro());
	}

	@Test
	public void processarEntidadeNomeComConstrutor() throws ProblemaDeCompilacao {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{ pedro[] \ndouglas<#pedro>[]}";
		PiconStore picon = Picon.construir(new ArrayDeque<Parte>(new Fragmentador(texto).getTokens()));
		Assert.assertEquals(2, picon.getStore().size());
		EntidadeComConstrutor douglas = picon.get("douglas");
		Assert.assertNotNull(douglas);
		Object pedro = picon.get("pedro");
		Assert.assertEquals(pedro, douglas.obterPedro());
	}

}
