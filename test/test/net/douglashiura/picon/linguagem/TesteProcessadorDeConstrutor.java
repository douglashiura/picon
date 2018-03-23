package test.net.douglashiura.picon.linguagem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Deque;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.Partes;
import net.douglashiura.picon.linguagem.atribuicao.ProcessadorDeConstrutor;
import net.douglashiura.picon.preguicoso.Objeto;
import net.douglashiura.picon.preguicoso.ParametroReferecia;
import net.douglashiura.picon.preguicoso.ParametroValor;
import test.net.douglashiura.picon.EntidadeComConstrutor;

public class TesteProcessadorDeConstrutor {

	private ProcessadorDeConstrutor atribuicoes;
	private Objeto<EntidadeComConstrutor> objeto;

	@Before
	public void setUp() throws ClassNotFoundException {
		objeto = new Objeto<>(EntidadeComConstrutor.class,null);
		atribuicoes = new ProcessadorDeConstrutor(objeto);
	}

	@Test
	public void valor() throws Exception {
		String texto = "<Douglas>";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		atribuicoes.processar(emQualificador);
		assertEquals(1, objeto.getParametros().size());
		assertEquals(0, objeto.getCampos().size());
		assertEquals(ParametroValor.class, objeto.getParametros().get(0).getClass());
		assertEquals("Douglas", objeto.getParametros().get(0).getValorDeclarado());
		assertTrue(emQualificador.isEmpty());
	}
	@Test
	public void referencia() throws Exception {
		String texto = "<#pedro>";
		Deque<Parte> emQualificador = Partes.explodir(texto);
		atribuicoes.processar(emQualificador);
		assertEquals(1, objeto.getParametros().size());
		assertEquals(0, objeto.getCampos().size());
		assertEquals(ParametroReferecia.class, objeto.getParametros().get(0).getClass());
		assertEquals("pedro", objeto.getParametros().get(0).getValorDeclarado());
		assertTrue(emQualificador.isEmpty());
	}

}
