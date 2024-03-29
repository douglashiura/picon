package test.net.douglashiura.picon.linguagem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.douglashiura.picon.linguagem.Parte;
import com.github.douglashiura.picon.linguagem.Partes;
import com.github.douglashiura.picon.linguagem.Qualificadores;
import com.github.douglashiura.picon.linguagem.atribuicao.ProcessadorDeLista;
import com.github.douglashiura.picon.linguagem.atribuicao.lista.EstrategiaReferencia;
import com.github.douglashiura.picon.linguagem.atribuicao.lista.EstrategiaValor;
import com.github.douglashiura.picon.preguicoso.Objeto;

import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.Enum;

public class TesteProcessadorDeLista {
	private Qualificadores qualificadores;
	private ProcessadorDeLista atribuicoes;

	@BeforeEach
	public void setUp() throws ClassNotFoundException {
		qualificadores = new Qualificadores();
	}

	@Test
	public void criandoEntidades() throws Exception {
		String klass = Entidade.class.getName();
		atribuicoes = new ProcessadorDeLista(klass, qualificadores, null);
		String texto = "{uid1[] uid2[]}";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator);
		Objeto<Entidade> uid1 = qualificadores.get("uid1");
		Objeto<Entidade> uid2 = qualificadores.get("uid2");
		assertEquals("[uid1, uid2]", atribuicoes.getEstrategia().getParametros().toString());
		assertEquals(0, uid1.getParametros().size());
		assertEquals(0, uid1.getCampos().size());
		assertEquals(0, uid2.getParametros().size());
		assertEquals(0, uid2.getCampos().size());
		assertEquals(EstrategiaReferencia.class, atribuicoes.getEstrategia().getClass());
		assertEquals(2, atribuicoes.getEstrategia().getParametros().size());
		assertEquals("uid1", atribuicoes.getEstrategia().getParametros().get(0));
		assertEquals("uid2", atribuicoes.getEstrategia().getParametros().get(1));
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void criandoEnuns() throws Exception {
		String klass = Enum.class.getName();
		atribuicoes = new ProcessadorDeLista(klass, qualificadores, null);
		String texto = "{A}";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator);
		assertEquals("[A]", atribuicoes.getEstrategia().getParametros().toString());
		assertEquals(EstrategiaValor.class, atribuicoes.getEstrategia().getClass());
		assertEquals(1, atribuicoes.getEstrategia().getParametros().size());
		assertEquals("A", atribuicoes.getEstrategia().getParametros().get(0));
		assertTrue(iterator.isEmpty());
	}

	@Test
	public void criandoStrings() throws Exception {
		String klass = String.class.getName();
		atribuicoes = new ProcessadorDeLista(klass, qualificadores, null);
		String texto = "{Douglas}";
		Deque<Parte> iterator = Partes.explodir(texto);
		atribuicoes.processar(iterator);
		assertEquals("[Douglas]", atribuicoes.getEstrategia().getParametros().toString());
		assertEquals(EstrategiaValor.class, atribuicoes.getEstrategia().getClass());
		assertEquals(1, atribuicoes.getEstrategia().getParametros().size());
		assertEquals("Douglas", atribuicoes.getEstrategia().getParametros().get(0));
		assertTrue(iterator.isEmpty());
	}
}
