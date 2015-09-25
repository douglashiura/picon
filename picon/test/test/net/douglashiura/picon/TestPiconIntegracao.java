/*
 * Douglas Hiura Longo, 14 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package test.net.douglashiura.picon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import net.douglashiura.picon.ExceptionCompilacao;
import net.douglashiura.picon.PiconStore;

import org.junit.Assert;
import org.junit.Test;

public class TestPiconIntegracao extends Assert {
	@Test
	public void test() throws ExceptionCompilacao, IOException,
			URISyntaxException {
		File file = new File(getClass().getResource("integracao").toURI());
		InputStream input = new FileInputStream(file);
		byte[] arquivo = new byte[input.available()];
		input.read(arquivo);
		input.close();

		PiconStore picon = PiconStore.build(new String(arquivo));
		assertEquals(22, picon.getStore().size());
		Entidade entidade = null;
		entidade = picon.get("UIDA");
		assertNull(entidade.getNome());
		assertNull(entidade.getIdade());
		assertNull(entidade.getEntidade());
		assertNull(entidade.getEntidades());

		entidade = picon.get("UID1");

		assertEquals("Douglas Hiura", entidade.getNome());
		assertEquals("792e72a0-6add-11e2-81bd-0019d288d6b2", entidade.getUuid()
				.toString());

		assertEquals(new Integer(10), entidade.getIdade());
		assertNull(entidade.getEntidade());
		assertNull(entidade.getEntidades());

		entidade = picon.get("UID5");
		assertEquals("Hiura5", entidade.getNome());
		assertNull(entidade.getIdade());
		assertNull(entidade.getEntidade());
		assertEquals(4, entidade.getEntidades().size());
		assertArrayEquals(
				entidade.getEntidades().toArray(new Entidade[4]),
				new Entidade[] { picon.get("UID6"), picon.get("UID7"),
						picon.get("UID8"), picon.get("UID9") });

		ETipo eTipo = picon.get("UID15");
		assertEquals(3, eTipo.getEntidades().size());
		assertArrayEquals(
				eTipo.getEntidades().toArray(new Entidade[3]),
				new Entidade[] { picon.get("UID17"), picon.get("UID2"),
						picon.get("UID3") });

	}

}
