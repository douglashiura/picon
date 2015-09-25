package test.net.douglashiura.picon;

import java.io.IOException;

import net.douglashiura.picon.ExceptionCompilacao;
import net.douglashiura.picon.PiconStore;

import org.junit.Test;

public class TestePiconStore {
	@Test
	public void build_semArquivo_piquinhos() throws IOException,
			ExceptionCompilacao {
		PiconStore.build("");
	}

}
