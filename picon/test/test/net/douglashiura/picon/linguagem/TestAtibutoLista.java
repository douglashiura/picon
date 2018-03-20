///*
// * Douglas Hiura Longo, 14 de Setembro de 2010.
// * 
// * twitter.com/douglashiura
// * java.inf.ufsc.br/dh
// * douglashiura.blogspot.com
// * douglashiura.parprimo.com
// * douglashiura@gmail.com
// * */
//package test.net.douglashiura.picon.linguagem;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import java.util.ArrayDeque;
//import java.util.Deque;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import net.douglashiura.picon.linguagem.Fragmentador;
//import net.douglashiura.picon.linguagem.Parte;
//import net.douglashiura.picon.linguagem.PiconLista;
//import net.douglashiura.picon.linguagem.Qualificadores;
//import net.douglashiura.picon.preguicoso.ContextoPreguisoso;
//import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;
//import test.net.douglashiura.picon.Entidade;
//import test.net.douglashiura.picon.EntidadeComConstrutor;
//
//public class TestAtibutoLista {
//
//	private Qualificadores qualificadores;
//	private ContextoPreguisoso contexto;
//	private EntidadeComConstrutor mane;
//
//	@Before
//	public void setUp() throws Exception {
//		ObjetoPreguicoso<EntidadeComConstrutor> maneObjeto = new ObjetoPreguicoso<>(EntidadeComConstrutor.class);
//		qualificadores.put("mane", maneObjeto);
//		contexto = new ContextoPreguisoso(qualificadores);
//		mane = contexto.get("mane");
//	}
//
//	@Test
//	public void umaListaVazia() throws Exception {
//		String texto = "{}";
//		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
//		new PiconLista(Entidade.class, iterator, qualificadores);
//	}
//
//	@Test
//	public void umaListaUmAtributoVazio() throws Exception {
//		String texto = "{uid[]}";
//		Deque<Parte> iterator = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
//		new PiconLista(Entidade.class, iterator, qualificadores);
//		assertNotNull(qualificadores.get("uid"));
//		assertEquals(Entidade.class, qualificadores.get("uid").getClass());
//	}
//
//	@Test
//	public void umaListaComConstrutorUmAtributoVazio() throws Exception {
//		String texto = "{douglas<#mane>[]}";
//		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
//		new PiconLista(EntidadeComConstrutor.class, tokens, qualificadores);
//		ObjetoPreguicoso<EntidadeComConstrutor> douglas = qualificadores.get("douglas");
//		assertEquals(mane, douglas.instanciar(contexto).obterPedro());
//	}
//
//	@Test
//	public void umaListaComConstrutorUmAtributoVazioComDois() throws Exception {
//		String texto = "{douglas<#mane>[] \n pedro[]}";
//		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
//		new PiconLista(EntidadeComConstrutor.class, tokens, qualificadores);
//		ObjetoPreguicoso<EntidadeComConstrutor> douglas = qualificadores.get("douglas");
//		assertEquals(mane, douglas.instanciar(contexto).obterPedro());
//		assertNotNull(qualificadores.get("pedro"));
//	}
//
//	@Test
//	public void umaListaComConstrutorUmAtributoVazioComDoisComMaisUm() throws Exception {
//		String texto = "{douglas<#mane>[] \n pedro[]\n douglas2<#mane>[]}";
//		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
//		new PiconLista(EntidadeComConstrutor.class, tokens, qualificadores);
//		ObjetoPreguicoso<EntidadeComConstrutor> douglas = qualificadores.get("douglas");
//		assertEquals(mane, douglas.instanciar(contexto).obterPedro());
//		assertNotNull(qualificadores.get("pedro"));
//		assertNotNull(qualificadores.get("douglas2"));
//	}
//
//	@Test
//	public void umaListaComConstrutorUmAtributoVazioComDoisInvertido() throws Exception {
//		String texto = "{pedro[] douglas<#mane>[]}";
//		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
//		new PiconLista(EntidadeComConstrutor.class, tokens, qualificadores);
//		ObjetoPreguicoso<EntidadeComConstrutor> douglas = qualificadores.get("douglas");
//		assertEquals(mane, douglas.instanciar(contexto).obterPedro());
//		assertNotNull(qualificadores.get("pedro"));
//	}
//
//	@Test
//	public void umaListaComConstrutorPrimitivoUmAtributoVazio() throws Exception {
//		String texto = "{douglas<Agua>[]}";
//		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
//		new PiconLista(EntidadeComConstrutor.class, tokens, qualificadores);
//		ObjetoPreguicoso<EntidadeComConstrutor> douglas = qualificadores.get("douglas");
//		EntidadeComConstrutor instanciar = douglas.instanciar(contexto);
//		assertEquals(null, instanciar.obterPedro());
//		assertEquals("Agua", instanciar.obterNome());
//	}
//
//	@Test
//	public void umaListaComConstrutorPrimitivoEReferenciaUmAtributoVazio() throws Exception {
//		String texto = "{douglas<Agua #mane>[]}";
//		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
//		new PiconLista(EntidadeComConstrutor.class, tokens, qualificadores);
//		ObjetoPreguicoso<EntidadeComConstrutor> douglas = qualificadores.get("douglas");
//		EntidadeComConstrutor instanciar = douglas.instanciar(contexto);
//		assertEquals(mane, instanciar.obterPedro());
//		assertEquals("Agua", instanciar.obterNome());
//	}
//
//	@Test
//	public void construtorDeLong() throws Exception {
//		String texto = "{douglas<10 Agua>[]}";
//		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
//		new PiconLista(EntidadeComConstrutor.class, tokens, qualificadores);
//		ObjetoPreguicoso<EntidadeComConstrutor> douglas = qualificadores.get("douglas");
//		EntidadeComConstrutor instanciar = douglas.instanciar(contexto);
//		assertEquals(10l, instanciar.obterNumero());
//		assertEquals("Agua", instanciar.obterNome());
//	}
//
//	@Test
//	public void umaListaComConstrutorReferenciaEPrimitivoUmAtributoVazio() throws Exception {
//		String texto = "{douglas<#mane Agua>[]}";
//		Deque<Parte> tokens = new ArrayDeque<Parte>(new Fragmentador(texto).getTokens());
//		new PiconLista(EntidadeComConstrutor.class, tokens, qualificadores);
//		ObjetoPreguicoso<EntidadeComConstrutor> douglas = qualificadores.get("douglas");
//		EntidadeComConstrutor instanciar = douglas.instanciar(contexto);
//		assertEquals(mane, instanciar.obterPedro());
//		assertEquals("Agua", instanciar.obterNome());
//	}
//
//}
