package net.douglashiura.picon.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

class FarejadorDeArquivos {

	private static final String EXTENSAO = ".picon";

	private static final FarejadorDeArquivos UNICO = new FarejadorDeArquivos();

	private String texto;

	private FarejadorDeArquivos() {
		texto = "";
		try {
			FarejadorDeArquivos.class.getClassLoader();
			Enumeration<URL> resources = ClassLoader.getSystemResources("");
			List<File> piconolosfilos = new ArrayList<File>();
			while (resources.hasMoreElements()) {
				URL url = (URL) resources.nextElement();
				ler(new File(url.toURI()), piconolosfilos);
			}

			StringBuffer all = new StringBuffer();
			for (File filos : piconolosfilos) {
				InputStream input = new FileInputStream(filos);
				byte[] arquivo = new byte[input.available()];
				input.read(arquivo);
				input.close();
				all.append(new String(arquivo));
			}
			texto = all.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void ler(File file, List<File> picons) {
		if (file.isFile() && file.getName().endsWith(EXTENSAO))
			picons.add(file);
		if (file.isDirectory())
			for (File filho : file.listFiles())
				ler(filho, picons);
	}

	public static FarejadorDeArquivos getInstance() {
		return UNICO;
	}

	@Override
	public String toString() {
		return texto;
	}

}
