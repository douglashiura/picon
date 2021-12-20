package test.net.douglashiura.picon.pregicoso;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.douglashiura.picon.linguagem.Qualificadores;
import com.github.douglashiura.picon.preguicoso.Contexto;
import com.github.douglashiura.picon.preguicoso.Objeto;

import test.net.douglashiura.picon.Entidade;

public class TesteContexto {
	private Qualificadores qualificadores;

	@BeforeEach
	public void setUp() {
		qualificadores = new Qualificadores();
		qualificadores.put("douglas", new Objeto<>(Entidade.class, null));
	}

	@Test
	public void umContexto() throws Exception {
		Contexto umContexto = new Contexto(qualificadores);
		Entidade douglas = umContexto.get("douglas");
		assertNotNull(douglas);
		assertSame(douglas, umContexto.get("douglas"));
	}

}
