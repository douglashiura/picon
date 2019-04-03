package test.net.douglashiura.picon.linguagem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Deque;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Partes;
import net.douglashiura.picon.linguagem.ProcessadorDeConjunto;
import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.Objeto;
import test.net.douglashiura.picon.Entidade;

public class TesteProcessadorDeConjunto {

	private Qualificadores qualificadores;
	private ProcessadorDeConjunto atribuicoes;

	@Before
	public void setUp() {
		qualificadores = new Qualificadores();
		atribuicoes = new ProcessadorDeConjunto(qualificadores);
	}

	@Test
	public void doisObjetos() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{uid1[] uid2[]}";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator);
		Objeto<Entidade> uid1 = qualificadores.get("uid1");
		Objeto<Entidade> uid2 = qualificadores.get("uid2");
		assertEquals(0, uid1.getParametros().size());
		assertEquals(0, uid1.getCampos().size());
		assertEquals(0, uid2.getParametros().size());
		assertEquals(0, uid2.getCampos().size());
		assertTrue(iterator.isEmpty());
	}
	

	@Test
	public void umObjetosComParrametro() throws Exception {
		String texto = "test.net.douglashiura.picon.EntidadeComConstrutor{uid2[] uid1<'Douglas' #uid2>[]}";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator);
		Objeto<Entidade> uid1 = qualificadores.get("uid1");
		Objeto<Entidade> uid2 = qualificadores.get("uid2");
		assertEquals(2, uid1.getParametros().size());
		assertEquals(0, uid1.getCampos().size());
		assertEquals(0, uid2.getParametros().size());
		assertEquals(0, uid2.getCampos().size());
		assertTrue(iterator.isEmpty());
	}
	
	
	@Test
	public void vazio() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{}";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator);
		assertTrue(iterator.isEmpty());
	}

}
