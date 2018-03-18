package test.net.douglashiura.picon.pregicoso;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;
import net.douglashiura.picon.preguicoso.Qualificadores;
import test.net.douglashiura.picon.Entidade;

public class TesteQualificadores {

	@Test
	public void umQualificador() throws Exception {
		Qualificadores qualificadores = new Qualificadores();
		ObjetoPreguicoso<?> objeto = new ObjetoPreguicoso<>(Entidade.class);
		qualificadores.put("douglas", objeto);
		assertSame(objeto, qualificadores.get("douglas"));
	}

}
