package test.net.douglashiura.picon.pregicoso;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.ClassePreguicosa;
import net.douglashiura.picon.preguicoso.ContextoPreguisoso;
import test.net.douglashiura.picon.Entidade;

public class TesteContextoPreguicoso {
	private Qualificadores qualificadores;

	@Before
	public void setUp() {
		qualificadores = new Qualificadores();
		ClassePreguicosa<Entidade> classe = new ClassePreguicosa<Entidade>(Entidade.class, qualificadores);
		classe.registre("douglas");
	}

	@Test
	public void umContexto() throws Exception {
		ContextoPreguisoso umContexto = new ContextoPreguisoso(qualificadores);
		Entidade douglas = umContexto.get("douglas");
		assertNotNull(douglas);
		assertSame(douglas, umContexto.get("douglas"));
	}

}
