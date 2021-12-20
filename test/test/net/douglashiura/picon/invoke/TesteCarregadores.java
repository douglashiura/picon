package test.net.douglashiura.picon.invoke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.github.douglashiura.picon.linguagem.Carregadores;

public class TesteCarregadores {

	@Test
	public void testName() throws Exception {

		assertEquals(TesteCarregadores.class,
				Carregadores.buscarClasse("test.net.douglashiura.picon.invoke.TesteCarregadores"));
		assertEquals(Integer.class, Carregadores.buscarClasse("java.lang.Integer"));

	}

	@Test
	public void naoExiste() throws Exception {
		assertThrows(ClassNotFoundException.class,
				() -> Carregadores.buscarClasse("test.net.douglashiura.picon.invoke.NAOEXISTE"));
	}

}
