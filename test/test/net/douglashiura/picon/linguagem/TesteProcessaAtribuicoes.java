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
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.linguagem.Fragmentador;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.linguagem.atribuicao.ProcessaAtribuicoes;
import net.douglashiura.picon.preguicoso.Campo;
import net.douglashiura.picon.preguicoso.CampoReferenciaLista;
import net.douglashiura.picon.preguicoso.CampoReferencia;
import net.douglashiura.picon.preguicoso.Objeto;
import net.douglashiura.picon.preguicoso.ParametroValor;
import net.douglashiura.picon.preguicoso.ParametroReferecia;
import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.EntidadeComConstrutor;

public class TesteProcessaAtribuicoes {

	private Qualificadores qualificadores;
	private Objeto<?> objetoPreguicoso;
	private ProcessaAtribuicoes atribuicoes;

	@Before
	public void setUp() {
		qualificadores = new Qualificadores();
		objetoPreguicoso = new Objeto<>(Entidade.class);
		atribuicoes = new ProcessaAtribuicoes(qualificadores);
	}

	@Test
	public void vazia() throws Exception {
		String texto = "[]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(0, objetoPreguicoso.getCampos().size());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void nome() throws Exception {
		String texto = "[nome=Douglas]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Campo campoNome = objetoPreguicoso.getCampos().get(0);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("nome", campoNome.getCampo());
		assertEquals("Douglas", campoNome.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void nomeIdade() throws Exception {
		String texto = "[nome=Douglas;idade=10]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Campo campoNome = objetoPreguicoso.getCampos().get(0);
		Campo campoIdade = objetoPreguicoso.getCampos().get(1);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(2, objetoPreguicoso.getCampos().size());
		assertEquals("nome", campoNome.getCampo());
		assertEquals("Douglas", campoNome.getValor());
		assertEquals("idade", campoIdade.getCampo());
		assertEquals("10", campoIdade.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void referencia() throws Exception {
		String texto = "[entidade #mane]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Campo campoEntidade = objetoPreguicoso.getCampos().get(0);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("entidade", campoEntidade.getCampo());
		assertEquals("mane", campoEntidade.getValor());
		assertEquals(CampoReferencia.class, campoEntidade.getClass());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeDeclaradaVazia() throws Exception {
		String texto = "[entidade uid3 []]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Campo campoEntidade = objetoPreguicoso.getCampos().get(0);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("entidade", campoEntidade.getCampo());
		assertEquals("uid3", campoEntidade.getValor());
		assertEquals(CampoReferencia.class, campoEntidade.getClass());
		assertNotNull(qualificadores.get("uid3"));
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessar() throws Exception {
		objetoPreguicoso = new Objeto<>(EntidadeComConstrutor.class);
		String texto = "[pedro=francisco <>[]]";
		Deque<Parte> emQualificador = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(emQualificador, objetoPreguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("pedro", objetoPreguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", objetoPreguicoso.getCampos().get(0).getValor());
		assertEquals(CampoReferencia.class, objetoPreguicoso.getCampos().get(0).getClass());
		assertEquals(0, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessarComUmParametro() throws Exception {
		objetoPreguicoso = new Objeto<>(EntidadeComConstrutor.class);
		String texto = "[pedro=francisco <Douglas>[]]";
		Deque<Parte> emQualificador = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(emQualificador, objetoPreguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("pedro", objetoPreguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", objetoPreguicoso.getCampos().get(0).getValor());
		assertEquals(1, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertEquals("Douglas", francisco.getParametros().get(0).getValorDeclarado());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessarComDoisParametro() throws Exception {
		objetoPreguicoso = new Objeto<>(EntidadeComConstrutor.class);
		String texto = "[pedro=francisco <10 Douglas>[]]";
		Deque<Parte> emQualificador = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(emQualificador, objetoPreguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("pedro", objetoPreguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", objetoPreguicoso.getCampos().get(0).getValor());
		assertEquals(2, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertEquals("10", francisco.getParametros().get(0).getValorDeclarado());
		assertEquals(ParametroValor.class, francisco.getParametros().get(0).getClass());
		assertEquals("Douglas", francisco.getParametros().get(1).getValorDeclarado());
		assertEquals(ParametroValor.class, francisco.getParametros().get(1).getClass());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessarComReferenciaParametro() throws Exception {
		objetoPreguicoso = new Objeto<>(EntidadeComConstrutor.class);
		String texto = "[pedro=francisco <#francisco>[]]";
		Deque<Parte> emQualificador = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(emQualificador, objetoPreguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("pedro", objetoPreguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", objetoPreguicoso.getCampos().get(0).getValor());
		assertEquals(1, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertEquals("francisco", francisco.getParametros().get(0).getValorDeclarado());
		assertEquals(ParametroReferecia.class, francisco.getParametros().get(0).getClass());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessarComReferenciaParametroString() throws Exception {
		objetoPreguicoso = new Objeto<>(EntidadeComConstrutor.class);
		String texto = "[pedro=francisco <Douglas #francisco>[]]";
		Deque<Parte> emQualificador = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(emQualificador, objetoPreguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("pedro", objetoPreguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", objetoPreguicoso.getCampos().get(0).getValor());
		assertEquals(2, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertEquals("Douglas", francisco.getParametros().get(0).getValorDeclarado());
		assertEquals(ParametroValor.class, francisco.getParametros().get(0).getClass());
		assertEquals("francisco", francisco.getParametros().get(1).getValorDeclarado());
		assertEquals(ParametroReferecia.class, francisco.getParametros().get(1).getClass());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void entidadeDeclaradaComLabelNome() throws Exception {
		String texto = "[entidade uid3 [nome=Douglas]]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Campo campoEntidade = objetoPreguicoso.getCampos().get(0);
		Objeto<Object> uid3 = qualificadores.get("uid3");
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("entidade", campoEntidade.getCampo());
		assertEquals("uid3", campoEntidade.getValor());
		assertNotNull(uid3);
		assertEquals(1, uid3.getCampos().size());
		assertEquals("nome", uid3.getCampos().get(0).getCampo());
		assertEquals("Douglas", uid3.getCampos().get(0).getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeListaVazia() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Campo campoEntidades = objetoPreguicoso.getCampos().get(0);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("entidades", campoEntidades.getCampo());
		assertEquals("[]", campoEntidades.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeListaEntidadeVazia() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{uid3[]}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Campo campoEntidades = objetoPreguicoso.getCampos().get(0);
		Objeto<Object> uid3 = qualificadores.get("uid3");
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("entidades", campoEntidades.getCampo());
		assertEquals("[uid3]", campoEntidades.getValor());
		assertNotNull(uid3);
		assertEquals(0, uid3.getCampos().size());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeListaEntidadeComNome() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{uid3[nome Douglas]}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Campo campoEntidades = objetoPreguicoso.getCampos().get(0);
		Objeto<Object> uid3 = qualificadores.get("uid3");
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("entidades", campoEntidades.getCampo());
		assertEquals("[uid3]", campoEntidades.getValor());
		assertNotNull(uid3);
		assertEquals(1, uid3.getCampos().size());
		assertEquals("nome", uid3.getCampos().get(0).getCampo());
		assertEquals("Douglas", uid3.getCampos().get(0).getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void umAtributoListaDeEnum() throws Exception {
		String texto = "[enums test.net.douglashiura.picon.Enum{A}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Campo campoEnuns = objetoPreguicoso.getCampos().get(0);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("enums", campoEnuns.getCampo());
		assertEquals("[A]", campoEnuns.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void umAtributoListaDeStringsPrimitivas() throws Exception {
		String texto = "[strings java.lang.String{a b c}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		List<Campo> campos = objetoPreguicoso.getCampos();
		Campo campoString = campos.get(0);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, campos.size());
		assertEquals("strings", campoString.getCampo());
		assertEquals("[a, b, c]", campoString.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void umAtributoListaDeStringsPrimitivasVazia() throws Exception {
		String texto = "[strings java.lang.String{}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		List<Campo> campos = objetoPreguicoso.getCampos();
		Campo campoString = campos.get(0);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, campos.size());
		assertEquals("strings", campoString.getCampo());
		assertEquals("[]", campoString.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeListaCom2EntidadeComNome() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{uid1[nome Douglas]uid2[nome Hiura]}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		List<Campo> campos = objetoPreguicoso.getCampos();
		Campo campoEntidades = campos.get(0);
		Objeto<Object> uid2 = qualificadores.get("uid2");
		Objeto<Object> uid1 = qualificadores.get("uid1");
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, campos.size());
		assertEquals("entidades", campoEntidades.getCampo());
		assertEquals("[uid1, uid2]", campoEntidades.getValor());
		assertEquals(1, uid1.getCampos().size());
		assertEquals("nome", uid1.getCampos().get(0).getCampo());
		assertEquals("Douglas", uid1.getCampos().get(0).getValor());
		assertEquals(1, uid2.getCampos().size());
		assertEquals("nome", uid2.getCampos().get(0).getCampo());
		assertEquals("Hiura", uid2.getCampos().get(0).getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeListaCom2Referencias() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{#uid1 #uid2}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Campo campoEntidades = objetoPreguicoso.getCampos().get(0);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals("entidades", campoEntidades.getCampo());
		assertEquals("[uid1, uid2]", campoEntidades.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeListaCom2EntidadeComNomeLista() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{uid1[nome Douglas; entidades test.net.douglashiura.picon.Entidade{}]uid2[nome Hiura;" + " entidades test.net.douglashiura.picon.Entidade{uid3[];#uid1;uid4[nome=Cabral]}]}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		atribuicoes.processar(iterator, objetoPreguicoso);
		Objeto<Entidade> uid1 = qualificadores.get("uid1");
		Objeto<Entidade> uid2 = qualificadores.get("uid2");
		Objeto<Entidade> uid3 = qualificadores.get("uid3");
		Objeto<Entidade> uid4 = qualificadores.get("uid4");
		assertEquals(1, objetoPreguicoso.getCampos().size());
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals("entidades", objetoPreguicoso.getCampos().get(0).getCampo());
		assertEquals("[uid1, uid2]", objetoPreguicoso.getCampos().get(0).getValor());
		assertEquals(CampoReferenciaLista.class, objetoPreguicoso.getCampos().get(0).getClass());
		assertEquals(0, uid1.getParametros().size());
		assertEquals(2, uid1.getCampos().size());
		assertEquals("nome", uid1.getCampos().get(0).getCampo());
		assertEquals("Douglas", uid1.getCampos().get(0).getValor());
		assertEquals("entidades", uid1.getCampos().get(1).getCampo());
		assertEquals("[]", uid1.getCampos().get(1).getValor());
		assertEquals(0, uid2.getParametros().size());
		assertEquals(2, uid2.getCampos().size());
		assertEquals("nome", uid2.getCampos().get(0).getCampo());
		assertEquals("Hiura", uid2.getCampos().get(0).getValor());
		assertEquals("entidades", uid2.getCampos().get(1).getCampo());
		assertEquals("[uid3, uid1, uid4]", uid2.getCampos().get(1).getValor());
		assertEquals(0, uid3.getParametros().size());
		assertEquals(0, uid3.getCampos().size());
		assertEquals(0, uid4.getParametros().size());
		assertEquals(1, uid4.getCampos().size());
		assertEquals("nome", uid4.getCampos().get(0).getCampo());
		assertEquals("Cabral", uid4.getCampos().get(0).getValor());
		assertTrue(iterator.isEmpty());
	}

}
