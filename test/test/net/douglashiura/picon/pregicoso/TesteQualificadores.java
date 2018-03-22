package test.net.douglashiura.picon.pregicoso;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.Objeto;
import test.net.douglashiura.picon.Entidade;

public class TesteQualificadores {

	@Test
	public void umQualificador() throws Exception {
		Qualificadores qualificadores = new Qualificadores();
		Objeto<?> objeto = new Objeto<>(Entidade.class);
		qualificadores.put("douglas", objeto);
		assertSame(objeto, qualificadores.get("douglas"));
	}

}
