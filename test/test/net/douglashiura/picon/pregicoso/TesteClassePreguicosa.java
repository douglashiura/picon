package test.net.douglashiura.picon.pregicoso;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import net.douglashiura.picon.preguicoso.ClassePreguicosa;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;
import test.net.douglashiura.picon.Entidade;

public class TesteClassePreguicosa {

	@Test
	public void umaClasse() throws Exception {
		new ClassePreguicosa<Entidade>(Entidade.class);
	}

	@Test
	public void umaObjetoPreguicoso() throws Exception {
		ClassePreguicosa<Entidade> classe = new ClassePreguicosa<Entidade>(Entidade.class);
		ObjetoPreguicoso<Entidade> objetoEntidade = classe.registre("douglas");
		assertNotNull(objetoEntidade);
		assertSame(objetoEntidade, classe.registre("douglas"));
		assertNotSame(objetoEntidade, classe.registre("douglas2"));
	}
}