package test.net.douglashiura.picon.invoke;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.douglashiura.picon.linguagem.Carregadores;

public class TesteCarregadores {

	@Test
	public void testName() throws Exception {
		
		assertEquals(TesteCarregadores.class,
				Carregadores.buscarClasse("test.net.douglashiura.picon.invoke.TesteCarregadores"));
		assertEquals(Integer.class,
				Carregadores.buscarClasse("java.lang.Integer"));
		
	}

	@Test(expected = ClassNotFoundException.class)
	public void naoExiste() throws Exception {
		Carregadores.buscarClasse("test.net.douglashiura.picon.invoke.NAOEXISTE");
	}

}
