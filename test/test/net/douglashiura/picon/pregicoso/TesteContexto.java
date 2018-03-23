package test.net.douglashiura.picon.pregicoso;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.Contexto;
import net.douglashiura.picon.preguicoso.Objeto;
import test.net.douglashiura.picon.Entidade;

public class TesteContexto {
	private Qualificadores qualificadores;

	@Before
	public void setUp() {
		qualificadores = new Qualificadores();
		qualificadores.put("douglas", new Objeto<>(Entidade.class,null));

	}

	@Test
	public void umContexto() throws Exception {
		Contexto umContexto = new Contexto(qualificadores);
		Entidade douglas = umContexto.get("douglas");
		assertNotNull(douglas);
		assertSame(douglas, umContexto.get("douglas"));
	}

}
