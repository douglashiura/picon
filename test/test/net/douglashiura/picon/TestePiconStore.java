package test.net.douglashiura.picon;

import java.io.IOException;

import net.douglashiura.picon.ProblemaDeCompilacao;
import net.douglashiura.picon.PiconStore;

import org.junit.Test;

public class TestePiconStore {
	@Test
	public void build_semArquivo_piquinhos() throws IOException,
			ProblemaDeCompilacao {
		PiconStore.build("");
	}

}
