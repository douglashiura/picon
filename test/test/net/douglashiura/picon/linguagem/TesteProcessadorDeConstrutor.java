package test.net.douglashiura.picon.linguagem;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.douglashiura.picon.linguagem.Parte;
import com.github.douglashiura.picon.linguagem.Partes;
import com.github.douglashiura.picon.linguagem.atribuicao.ProcessadorDeConstrutor;
import com.github.douglashiura.picon.preguicoso.Objeto;
import com.github.douglashiura.picon.preguicoso.ParametroReferecia;
import com.github.douglashiura.picon.preguicoso.ParametroValor;

import test.net.douglashiura.picon.EntidadeComConstrutor;

public class TesteProcessadorDeConstrutor {

	private ProcessadorDeConstrutor atribuicoes;
	private Objeto<EntidadeComConstrutor> objeto;

	@BeforeEach
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
