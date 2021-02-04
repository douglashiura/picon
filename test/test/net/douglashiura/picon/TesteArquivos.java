package test.net.douglashiura.picon;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.douglashiura.picon.linguagem.Arquivos;

public class TesteArquivos {

	@Test
	public void unico() throws Exception {
		Arquivos testes = Arquivos.getInstance();
		assertNotNull(testes);
		assertSame(testes, Arquivos.getInstance());
		assertNotNull(testes.explodir());
		assertSame(testes.explodir(),testes.explodir());
		assertNotNull(testes.explodir().get("injetada:pelo:picon"));
	}
}
