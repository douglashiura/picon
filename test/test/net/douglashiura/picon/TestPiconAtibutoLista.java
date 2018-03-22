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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.douglashiura.picon.Fragmentador;
import net.douglashiura.picon.Parte;
import net.douglashiura.picon.PiconLista;
import net.douglashiura.picon.PiconStore;

public class TestPiconAtibutoLista {

	@Test
	public void umaListaVazia() throws Exception {
		String texto = "{}";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokes());
		PiconLista<Entidade> picon = new PiconLista<Entidade>(Entidade.class, iterator, contexto());
		Assert.assertEquals(0, picon.obterObjeto().size());
	}

	@Test
	public void umaListaUmAtributoVazio() throws Exception {
		String texto = "{UID[]}";
		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokes());
		PiconLista<Entidade> picon = new PiconLista<Entidade>(Entidade.class, iterator, contexto());

		List<Entidade> lista = picon.obterObjeto();
		assertEquals(1, lista.size());
		assertNotNull(lista.get(0));

	}

	@Test
	public void umaListaComConstrutorUmAtributoVazio() throws Exception {
		String texto = "{douglas<#mane>[]}";
		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokes());
		PiconStore store = contexto();
		EntidadeComConstrutor mane = new EntidadeComConstrutor();
		violaAcesso(store, mane);
		PiconLista<EntidadeComConstrutor> picon = new PiconLista<EntidadeComConstrutor>(EntidadeComConstrutor.class,
				tokens, store);
		List<EntidadeComConstrutor> lista = picon.obterObjeto();
		assertEquals(1, lista.size());
		assertEquals(mane, lista.get(0).obterPedro());
	}

	@Test
	public void umaListaComConstrutorUmAtributoVazioComDois() throws Exception {
		String texto = "{douglas<#mane>[] \n pedro[]}";
		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokes());
		PiconStore store = contexto();
		EntidadeComConstrutor mane = new EntidadeComConstrutor();
		violaAcesso(store, mane);
		PiconLista<EntidadeComConstrutor> picon = new PiconLista<EntidadeComConstrutor>(EntidadeComConstrutor.class,
				tokens, store);
		List<EntidadeComConstrutor> lista = picon.obterObjeto();
		assertEquals(2, lista.size());
		assertEquals(mane, lista.get(0).obterPedro());
	}

	@Test
	public void umaListaComConstrutorUmAtributoVazioComDoisComMaisUm() throws Exception {
		String texto = "{douglas<#mane>[] \n pedro[]\n douglas2<#mane>[]}";
		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokes());
		PiconStore store = contexto();
		EntidadeComConstrutor mane = new EntidadeComConstrutor();
		violaAcesso(store, mane);
		PiconLista<EntidadeComConstrutor> picon = new PiconLista<EntidadeComConstrutor>(EntidadeComConstrutor.class,
				tokens, store);
		List<EntidadeComConstrutor> lista = picon.obterObjeto();
		assertEquals(3, lista.size());
		assertEquals(mane, lista.get(0).obterPedro());
	}

	@Test
	public void umaListaComConstrutorUmAtributoVazioComDoisInvertido() throws Exception {
		String texto = "{pedro[] douglas<#mane>[]}";
		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokes());
		PiconStore store = contexto();
		EntidadeComConstrutor mane = new EntidadeComConstrutor();
		violaAcesso(store, mane);
		PiconLista<EntidadeComConstrutor> picon = new PiconLista<EntidadeComConstrutor>(EntidadeComConstrutor.class,
				tokens, store);
		List<EntidadeComConstrutor> lista = picon.obterObjeto();
		assertEquals(2, lista.size());
		assertEquals(mane, lista.get(1).obterPedro());
	}

	@Test
	public void umaListaComConstrutorPrimitivoUmAtributoVazio() throws Exception {
		String texto = "{douglas<Agua>[]}";
		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokes());
		PiconStore store = contexto();
		PiconLista<EntidadeComConstrutor> picon = new PiconLista<EntidadeComConstrutor>(EntidadeComConstrutor.class,
				tokens, store);
		List<EntidadeComConstrutor> lista = picon.obterObjeto();
		assertEquals(1, lista.size());
		assertEquals("Agua", lista.get(0).obterNome());
	}

	@Test
	public void umaListaComConstrutorPrimitivoEReferenciaUmAtributoVazio() throws Exception {
		String texto = "{douglas<Agua #mane>[]}";
		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokes());
		PiconStore store = contexto();
		EntidadeComConstrutor mane = new EntidadeComConstrutor();
		violaAcesso(store, mane);
		PiconLista<EntidadeComConstrutor> picon = new PiconLista<EntidadeComConstrutor>(EntidadeComConstrutor.class,
				tokens, store);
		List<EntidadeComConstrutor> lista = picon.obterObjeto();
		assertEquals(1, lista.size());
		assertEquals("Agua", lista.get(0).obterNome());
		assertEquals(mane, lista.get(0).obterPedro());
	}

	@Test
	public void construtorDeLong() throws Exception {
		String texto = "{douglas<10 Agua>[]}";
		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokes());
		PiconStore store = contexto();
		EntidadeComConstrutor mane = new EntidadeComConstrutor();
		violaAcesso(store, mane);
		PiconLista<EntidadeComConstrutor> picon = new PiconLista<EntidadeComConstrutor>(EntidadeComConstrutor.class,
				tokens, store);
		List<EntidadeComConstrutor> lista = picon.obterObjeto();
		assertEquals(1, lista.size());
		assertEquals(10l, lista.get(0).obterNumero());
		assertEquals("Agua", lista.get(0).obterNome());
	}

	@Test
	public void umaListaComConstrutorReferenciaEPrimitivoUmAtributoVazio() throws Exception {
		String texto = "{douglas<#mane Agua>[]}";
		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokes());
		PiconStore store = contexto();
		EntidadeComConstrutor mane = new EntidadeComConstrutor();
		violaAcesso(store, mane);
		PiconLista<EntidadeComConstrutor> picon = new PiconLista<EntidadeComConstrutor>(EntidadeComConstrutor.class,
				tokens, store);
		List<EntidadeComConstrutor> lista = picon.obterObjeto();
		assertEquals(1, lista.size());
		assertEquals("Agua", lista.get(0).obterNome());
		assertEquals(mane, lista.get(0).obterPedro());
	}

	private PiconStore contexto() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Constructor<PiconStore> construtor = PiconStore.class.getDeclaredConstructor();
		construtor.setAccessible(true);
		return construtor.newInstance();
	}

	private void violaAcesso(PiconStore store, EntidadeComConstrutor mane) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method metodo = store.getClass().getDeclaredMethod("adicionar", String.class, Object.class);
		metodo.setAccessible(true);
		metodo.invoke(store, "mane", mane);
	}

}
