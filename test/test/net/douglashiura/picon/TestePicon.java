package test.net.douglashiura.picon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Deque;

import org.junit.Test;

import com.github.douglashiura.picon.Picon;
import com.github.douglashiura.picon.ProblemaDeCompilacaoException;
import com.github.douglashiura.picon.linguagem.Parte;
import com.github.douglashiura.picon.linguagem.Partes;
import com.github.douglashiura.picon.linguagem.Qualificadores;
import com.github.douglashiura.picon.preguicoso.Contexto;
import com.github.douglashiura.picon.preguicoso.Objeto;

public class TestePicon {

	@Test
	public void doisObjetos() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{uid1[] uid2[]}";
		Deque<Parte> iterator = Partes.explodir(texto);
		Qualificadores qualificadores = Picon.explodir(iterator);
		Objeto<Entidade> uid1 = qualificadores.get("uid1");
		Objeto<Entidade> uid2 = qualificadores.get("uid2");
		assertEquals(0, uid1.getParametros().size());
		assertEquals(0, uid1.getCampos().size());
		assertEquals(0, uid2.getParametros().size());
		assertEquals(0, uid2.getCampos().size());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void duasClasses() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{uid1[]}test.net.douglashiura.picon.Entidade{uid2[]}";
		Deque<Parte> iterator = Partes.explodir(texto);
		Qualificadores qualificadores = Picon.explodir(iterator);
		Objeto<Entidade> uid1 = qualificadores.get("uid1");
		Objeto<Entidade> uid2 = qualificadores.get("uid2");
		assertEquals(0, uid1.getParametros().size());
		assertEquals(0, uid1.getCampos().size());
		assertEquals(0, uid2.getParametros().size());
		assertEquals(0, uid2.getCampos().size());
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void vazio() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{}";
		Deque<Parte> iterator = Partes.explodir(texto);
		Picon.explodir(iterator);
		assertTrue(iterator.isEmpty());
	}

	@Test(expected=ProblemaDeCompilacaoException.class)
	public void semClasse() throws Exception {
		String texto = "test.net.douglashiura.picon.NAO_EXISTE{}";
		Deque<Parte> iterator = Partes.explodir(texto);
		Picon.explodir(iterator);
	}
	@Test(expected=ProblemaDeCompilacaoException.class)
	public void semCampo() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{uid[NAO_EXISTE=10]}";
		Deque<Parte> iterator = Partes.explodir(texto);
		Qualificadores qualificadores = Picon.explodir(iterator);
		new Contexto(qualificadores).get("uid");
		
	}
	
	@Test(expected=ProblemaDeCompilacaoException.class)
	public void intrucaoInvalida() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{a[entidades test.net.douglashiura.picon.Entidade{1=1}]}";
		Deque<Parte> iterator = Partes.explodir(texto);
		Picon.explodir(iterator);
	}
	@Test(expected=ProblemaDeCompilacaoException.class)
	public void incompleta() throws Exception {
		String texto = "test.net.douglashiura.picon.Entidade{";
		Deque<Parte> iterator = Partes.explodir(texto);
		Picon.explodir(iterator);
	}

}
