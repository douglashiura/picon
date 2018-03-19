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
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.PiconStore;
import net.douglashiura.picon.linguagem.Fragmentador;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.PiconAtributoEntidade;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.CampoPreguisoso;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;

public class TestPiconAtributoEntidade {

	private Qualificadores qualificadores;
	private ObjetoPreguicoso<Entidade> objetoPreguicoso;

	@Before
	public void setUp() {
		qualificadores = new Qualificadores();
		objetoPreguicoso = new ObjetoPreguicoso<>(Entidade.class);
	}

	@Test
	public void vazia() throws Exception {
		String texto = "[]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		new PiconAtributoEntidade(iterator, objetoPreguicoso, qualificadores);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(0, objetoPreguicoso.getCampos().size());
	}

	@Test
	public void nome() throws Exception {
		String texto = "[nome=Douglas]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		new PiconAtributoEntidade(iterator, objetoPreguicoso, qualificadores);
		List<CampoPreguisoso> campos = objetoPreguicoso.getCampos();
		CampoPreguisoso campoNome = campos.get(0);
		assertEquals(0, objetoPreguicoso.getParametros().size());
		assertEquals(1, campos.size());
		assertEquals("nome", campoNome.getCampo());
		assertEquals("Douglas", campoNome.getValor());
	}

	@Test
	public void nomeIdade() throws Exception {
		String texto = "[nome=Douglas;idade=10]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, contexto());
		assertNotNull(picon.obterObjeto());
		assertEquals("Douglas", picon.obterObjeto().getNome());
		assertEquals(new Integer(10), picon.obterObjeto().getIdade());
	}

	@Test
	public void referencia() throws Exception {
		String texto = "[entidade #mane]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconStore cont;
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, cont = contexto());
		violaAcesso(cont, new Entidade());
		soldar(cont);
		assertNotNull(picon.obterObjeto());
		assertNotNull(picon.obterObjeto().getEntidade());
	}

	private void soldar(PiconStore contexto) throws Exception {
		Method method = contexto.getClass().getDeclaredMethod("soldar");
		method.setAccessible(true);
		method.invoke(contexto);

	}

	private PiconStore contexto() throws Exception {
		Constructor<PiconStore> construtor = PiconStore.class.getDeclaredConstructor();
		construtor.setAccessible(true);
		return construtor.newInstance();
	}

	@Test
	public void entidadeDeclaradaVazia() throws Exception {
		String texto = "[entidade uid3 []]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, null);
		assertNotNull(picon.obterObjeto());
		assertNotNull(picon.obterObjeto().getEntidade());
	}

	@Test
	public void entidadeDeclaradaComLabelNome() throws Exception {
		String texto = "[entidade uid3 [nome=Douglas]]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, contexto());
		assertNotNull(picon.obterObjeto());
		assertNotNull(picon.obterObjeto().getEntidade());
	}

	@Test
	public void entidadeListaVazia() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, null);
		assertNotNull(picon.obterObjeto());
		assertNotNull(picon.obterObjeto().getEntidades());
		assertTrue(picon.obterObjeto().getEntidades().isEmpty());

	}

	@Test
	public void entidadeListaEntidadeVazia() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{UID[]}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, contexto());
		assertNotNull(picon.obterObjeto());
		assertEquals(1, picon.obterObjeto().getEntidades().size());
	}

	@Test
	public void entidadeListaEntidadeComNome() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{UID[nome Douglas]}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, contexto());
		assertNotNull(picon.obterObjeto());
		assertEquals(1, picon.obterObjeto().getEntidades().size());
		assertEquals("Douglas", picon.obterObjeto().getEntidades().get(0).getNome());
	}

	@Test
	public void umAtributoListaDeEnum() throws Exception {
		String texto = "[enums test.net.douglashiura.picon.Enum{A}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, contexto());
		assertNotNull(picon.obterObjeto());
		assertEquals(1, picon.obterObjeto().getEnums().size());
		assertEquals(Enum.A, picon.obterObjeto().getEnums().get(0));
	}

	@Test
	public void umAtributoListaDeStringsPrimitivas() throws Exception {
		String texto = "[strings String{a b c}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, contexto());
		assertNotNull(picon.obterObjeto());
		assertEquals(3, picon.obterObjeto().getStrings().size());
		assertEquals("a", picon.obterObjeto().getStrings().get(0));
		assertEquals("b", picon.obterObjeto().getStrings().get(1));
		assertEquals("c", picon.obterObjeto().getStrings().get(2));
	}

	@Test
	public void umAtributoListaDeStringsPrimitivasVazia() throws Exception {
		String texto = "[strings String{}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, contexto());
		assertNotNull(picon.obterObjeto());
		assertTrue(picon.obterObjeto().getStrings().isEmpty());

	}

	@Test
	public void entidadeListaCom2EntidadeComNome() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{UID[nome Douglas]UID[nome Hiura]}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, contexto());
		assertNotNull(picon.obterObjeto());
		assertEquals(2, picon.obterObjeto().getEntidades().size());
		assertEquals("Douglas", picon.obterObjeto().getEntidades().get(0).getNome());
		assertEquals("Hiura", picon.obterObjeto().getEntidades().get(1).getNome());
	}

	@Test
	public void entidadeListaCom2EntidadeComNomeLista() throws Exception {
		String texto = "[entidades test.net.douglashiura.picon.Entidade{UID[nome Douglas; entidades test.net.douglashiura.picon.Entidade{}]UID[nome Hiura; entidades test.net.douglashiura.picon.Entidade{UID[];#UID;UID3[nome=douglas] } ]}]";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
		PiconStore cont;
		PiconAtributoEntidade picon = new PiconAtributoEntidade(Entidade.class, iterator, cont = contexto());
		soldar(cont);
		assertNotNull(picon.obterObjeto());
		assertEquals(2, picon.obterObjeto().getEntidades().size());
		assertEquals("Douglas", picon.obterObjeto().getEntidades().get(0).getNome());
		assertTrue(picon.obterObjeto().getEntidades().get(0).getEntidades().isEmpty());
		assertEquals("Hiura", picon.obterObjeto().getEntidades().get(1).getNome());
		assertEquals(3, picon.obterObjeto().getEntidades().get(1).getEntidades().size());
	}

}
