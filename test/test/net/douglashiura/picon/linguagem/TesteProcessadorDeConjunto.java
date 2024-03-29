package test.net.douglashiura.picon.linguagem;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.douglashiura.picon.linguagem.Parte;
import com.github.douglashiura.picon.linguagem.Partes;
import com.github.douglashiura.picon.linguagem.ProcessadorDeConjunto;
import com.github.douglashiura.picon.linguagem.Qualificadores;
import com.github.douglashiura.picon.preguicoso.Objeto;

import test.net.douglashiura.picon.Entidade;

public class TesteProcessadorDeConjunto {

	private Qualificadores qualificadores;
	private ProcessadorDeConjunto atribuicoes;

	@BeforeEach
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
