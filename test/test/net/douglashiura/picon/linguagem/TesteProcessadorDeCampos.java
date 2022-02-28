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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Deque;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.douglashiura.picon.ProblemaDeCompilacaoException;
import com.github.douglashiura.picon.linguagem.Parte;
import com.github.douglashiura.picon.linguagem.Partes;
import com.github.douglashiura.picon.linguagem.Qualificadores;
import com.github.douglashiura.picon.linguagem.atribuicao.ProcessadorDeCampos;
import com.github.douglashiura.picon.preguicoso.Campo;
import com.github.douglashiura.picon.preguicoso.CampoReferencia;
import com.github.douglashiura.picon.preguicoso.CampoReferenciaLista;
import com.github.douglashiura.picon.preguicoso.Contexto;
import com.github.douglashiura.picon.preguicoso.Objeto;
import com.github.douglashiura.picon.preguicoso.ParametroReferecia;
import com.github.douglashiura.picon.preguicoso.ParametroValor;

import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.EntidadeComConstrutor;

public class TesteProcessadorDeCampos {

	private Qualificadores qualificadores;
	private Objeto<?> preguicoso;
	private ProcessadorDeCampos atribuicoes;

	@BeforeEach
	public void setUp() {
		qualificadores = new Qualificadores();
		preguicoso = new Objeto<>(Entidade.class, null);
		atribuicoes = new ProcessadorDeCampos(qualificadores);
	}

	@Test
	public void vazia() throws Exception {
		String texto = "[]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(0, preguicoso.getCampos().size());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void nome() throws Exception {
		String texto = "[nome=Douglas]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoNome = preguicoso.getCampos().get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("nome", campoNome.getCampo());
		assertEquals("Douglas", campoNome.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void classePaiSuperNome() throws Exception {
		String texto = "[superNome=Douglas]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoNome = preguicoso.getCampos().get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("superNome", campoNome.getCampo());
		assertEquals("Douglas", campoNome.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void nomeIdade() throws Exception {
		String texto = "[nome=Douglas;idade=10]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoNome = preguicoso.getCampos().get(0);
		Campo campoIdade = preguicoso.getCampos().get(1);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(2, preguicoso.getCampos().size());
		assertEquals("nome", campoNome.getCampo());
		assertEquals("Douglas", campoNome.getValor());
		assertEquals("idade", campoIdade.getCampo());
		assertEquals("10", campoIdade.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void referencia() throws Exception {
		String texto = "[entidade #mane]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoEntidade = preguicoso.getCampos().get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("entidade", campoEntidade.getCampo());
		assertEquals("mane", campoEntidade.getValor());
		assertEquals(CampoReferencia.class, campoEntidade.getClass());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void referenciaSuperClasse() throws Exception {
		String texto = "[superEntidade #mane]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoEntidade = preguicoso.getCampos().get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("superEntidade", campoEntidade.getCampo());
		assertEquals("mane", campoEntidade.getValor());
		assertEquals(CampoReferencia.class, campoEntidade.getClass());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeDeclaradaSuperClasse() throws Exception {
		String texto = "[superEntidade uid3 []]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoEntidade = preguicoso.getCampos().get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("superEntidade", campoEntidade.getCampo());
		assertEquals("uid3", campoEntidade.getValor());
		assertEquals(CampoReferencia.class, campoEntidade.getClass());
		assertNotNull(qualificadores.get("uid3"));
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeDeclaradaNaoExisteOCampo() throws Exception {
		String texto = "[naoExiste uid3 []]";
		Deque<Parte> iterator = Partes.explodir(texto);
		assertThrows(ProblemaDeCompilacaoException.class, () -> atribuicoes.processar(iterator, preguicoso));

	}

	@Test
	public void entidadeDeclaradaVazia() throws Exception {
		String texto = "[entidade uid3 []]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoEntidade = preguicoso.getCampos().get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("entidade", campoEntidade.getCampo());
		assertEquals("uid3", campoEntidade.getValor());
		assertEquals(CampoReferencia.class, campoEntidade.getClass());
		assertNotNull(qualificadores.get("uid3"));
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessar() throws Exception {
		preguicoso = new Objeto<>(EntidadeComConstrutor.class, null);
		String texto = "[pedro=francisco <>[]]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		atribuicoes.processar(emQualificador, preguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("pedro", preguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", preguicoso.getCampos().get(0).getValor());
		assertEquals(CampoReferencia.class, preguicoso.getCampos().get(0).getClass());
		assertEquals(0, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessarComUmParametro() throws Exception {
		preguicoso = new Objeto<>(EntidadeComConstrutor.class, null);
		String texto = "[pedro=francisco <Douglas>[]]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		atribuicoes.processar(emQualificador, preguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("pedro", preguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", preguicoso.getCampos().get(0).getValor());
		assertEquals(1, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertEquals("Douglas", francisco.getParametros().get(0).getValorDeclarado());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessarComDoisParametro() throws Exception {
		preguicoso = new Objeto<>(EntidadeComConstrutor.class, null);
		String texto = "[pedro=francisco <10 Douglas>[]]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		atribuicoes.processar(emQualificador, preguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("pedro", preguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", preguicoso.getCampos().get(0).getValor());
		assertEquals(2, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertEquals("10", francisco.getParametros().get(0).getValorDeclarado());
		assertEquals(ParametroValor.class, francisco.getParametros().get(0).getClass());
		assertEquals("Douglas", francisco.getParametros().get(1).getValorDeclarado());
		assertEquals(ParametroValor.class, francisco.getParametros().get(1).getClass());
		assertTrue(emQualificador.isEmpty());
	}
	@Test
	public void compostoComConstrutorProcessarComUmParametroInicio() throws Exception {
		preguicoso = new Objeto<>(EntidadeComConstrutor.class, null);
		String texto = "<'Douglas'>[]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		atribuicoes.processar(emQualificador, preguicoso);
		assertEquals(1, preguicoso.getParametros().size());
		assertEquals(0, preguicoso.getCampos().size());
		assertEquals("Douglas", preguicoso.getParametros().get(0).getValorDeclarado());
		assertEquals(ParametroValor.class, preguicoso.getParametros().get(0).getClass());
		assertTrue(emQualificador.isEmpty());
	}
	@Test
	public void compostoComConstrutorProcessarComDoisParametroInicio() throws Exception {
		preguicoso = new Objeto<>(EntidadeComConstrutor.class, null);
		String texto = "<10 'Douglas'>[]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		atribuicoes.processar(emQualificador, preguicoso);
		assertEquals(2, preguicoso.getParametros().size());
		assertEquals(0, preguicoso.getCampos().size());
		assertEquals("10", preguicoso.getParametros().get(0).getValorDeclarado());
		assertEquals(ParametroValor.class, preguicoso.getParametros().get(0).getClass());
		assertEquals("Douglas", preguicoso.getParametros().get(1).getValorDeclarado());
		assertEquals(ParametroValor.class, preguicoso.getParametros().get(1).getClass());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessarComReferenciaParametro() throws Exception {
		preguicoso = new Objeto<>(EntidadeComConstrutor.class, null);
		String texto = "[pedro=francisco <#francisco>[]]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		atribuicoes.processar(emQualificador, preguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("pedro", preguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", preguicoso.getCampos().get(0).getValor());
		assertEquals(1, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertEquals("francisco", francisco.getParametros().get(0).getValorDeclarado());
		assertEquals(ParametroReferecia.class, francisco.getParametros().get(0).getClass());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessarComReferenciaParametroString() throws Exception {
		preguicoso = new Objeto<>(EntidadeComConstrutor.class, null);
		String texto = "[pedro=francisco <Douglas #francisco>[]]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		atribuicoes.processar(emQualificador, preguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("pedro", preguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", preguicoso.getCampos().get(0).getValor());
		assertEquals(2, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertEquals("Douglas", francisco.getParametros().get(0).getValorDeclarado());
		assertEquals(ParametroValor.class, francisco.getParametros().get(0).getClass());
		assertEquals("francisco", francisco.getParametros().get(1).getValorDeclarado());
		assertEquals(ParametroReferecia.class, francisco.getParametros().get(1).getClass());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void compostoComConstrutorProcessarComReferenciaParametroStringInvert() throws Exception {
		preguicoso = new Objeto<>(EntidadeComConstrutor.class, null);
		String texto = "[pedro=francisco <#francisco 'Douglas'>[]]";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		atribuicoes.processar(emQualificador, preguicoso);
		Objeto<Object> francisco = qualificadores.get("francisco");
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("pedro", preguicoso.getCampos().get(0).getCampo());
		assertEquals("francisco", preguicoso.getCampos().get(0).getValor());
		assertEquals(2, francisco.getParametros().size());
		assertEquals(0, francisco.getCampos().size());
		assertEquals("Douglas", francisco.getParametros().get(1).getValorDeclarado());
		assertEquals(ParametroValor.class, francisco.getParametros().get(1).getClass());
		assertEquals("francisco", francisco.getParametros().get(0).getValorDeclarado());
		assertEquals(ParametroReferecia.class, francisco.getParametros().get(0).getClass());
		assertTrue(emQualificador.isEmpty());
	}

	@Test
	public void entidadeDeclaradaComLabelNome() throws Exception {
		String texto = "[entidade uid3 [nome=Douglas]]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoEntidade = preguicoso.getCampos().get(0);
		Objeto<Object> uid3 = qualificadores.get("uid3");
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
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
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoEntidades = preguicoso.getCampos().get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("entidades", campoEntidades.getCampo());
		assertEquals("[]", campoEntidades.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeListaEntidadeVazia() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{uid3[]}]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoEntidades = preguicoso.getCampos().get(0);
		Objeto<Object> uid3 = qualificadores.get("uid3");
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("entidades", campoEntidades.getCampo());
		assertEquals("[uid3]", campoEntidades.getValor());
		assertNotNull(uid3);
		assertEquals(0, uid3.getCampos().size());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeListaEntidadeComNome() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{uid3[nome Douglas]}]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoEntidades = preguicoso.getCampos().get(0);
		Objeto<Object> uid3 = qualificadores.get("uid3");
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
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
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoEnuns = preguicoso.getCampos().get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("enums", campoEnuns.getCampo());
		assertEquals("[A]", campoEnuns.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void umAtributoListaDeStringsPrimitivas() throws Exception {
		String texto = "[strings java.lang.String{a b c}]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		List<Campo> campos = preguicoso.getCampos();
		Campo campoString = campos.get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, campos.size());
		assertEquals("strings", campoString.getCampo());
		assertEquals("[a, b, c]", campoString.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void umAtributoListaDeStringsPrimitivasVazia() throws Exception {
		String texto = "[strings java.lang.String{}]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		List<Campo> campos = preguicoso.getCampos();
		Campo campoString = campos.get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, campos.size());
		assertEquals("strings", campoString.getCampo());
		assertEquals("[]", campoString.getValor());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void entidadeListaCom2EntidadeComNome() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{uid1[nome Douglas]uid2[nome Hiura]}]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		List<Campo> campos = preguicoso.getCampos();
		Campo campoEntidades = campos.get(0);
		Objeto<Object> uid2 = qualificadores.get("uid2");
		Objeto<Object> uid1 = qualificadores.get("uid1");
		assertEquals(0, preguicoso.getParametros().size());
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
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Campo campoEntidades = preguicoso.getCampos().get(0);
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals("entidades", campoEntidades.getCampo());
		assertEquals("[uid1, uid2]", campoEntidades.getValor());
		assertTrue(iterator.isEmpty());
	}
	
	@Test
	public void entidadeComConstrutorString() throws Exception {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{ douglas<Douglas>[] }";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Objeto<EntidadeComConstrutor> entidade = qualificadores.get("douglas");
		Contexto contexto = new Contexto(qualificadores);
		assertEquals("Douglas", entidade.instanciar(contexto));
	}


	@Test
	public void entidadeListaCom2EntidadeComNomeLista() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{uid1[nome Douglas; entidades test.net.douglashiura.picon.Entidade{}]uid2[nome Hiura;"
				+ " entidades test.net.douglashiura.picon.Entidade{uid3[];#uid1;uid4[nome=Cabral]}]}]";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator, preguicoso);
		Objeto<Entidade> uid1 = qualificadores.get("uid1");
		Objeto<Entidade> uid2 = qualificadores.get("uid2");
		Objeto<Entidade> uid3 = qualificadores.get("uid3");
		Objeto<Entidade> uid4 = qualificadores.get("uid4");
		assertEquals(1, preguicoso.getCampos().size());
		assertEquals(0, preguicoso.getParametros().size());
		assertEquals("entidades", preguicoso.getCampos().get(0).getCampo());
		assertEquals("[uid1, uid2]", preguicoso.getCampos().get(0).getValor());
		assertEquals(CampoReferenciaLista.class, preguicoso.getCampos().get(0).getClass());
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
