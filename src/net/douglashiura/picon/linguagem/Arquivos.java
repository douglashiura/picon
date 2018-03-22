package net.douglashiura.picon.linguagem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Arquivos {

	private static final String EXTENSAO = ".picon";
	private static Arquivos unico;
	private Qualificadores qualificadores;

	private Arquivos() {
		
		try {
			Arquivos.class.getClassLoader();
			Enumeration<URL> resources = ClassLoader.getSystemResources("");
			List<File> arquivos = new ArrayList<File>();
			while (resources.hasMoreElements()) {
				URL url = (URL) resources.nextElement();
				ler(new File(url.toURI()), arquivos);
			}

			StringBuffer texto = new StringBuffer();
			for (File filos : arquivos) {
				InputStream input = new FileInputStream(filos);
				byte[] arquivo = new byte[input.available()];
				input.read(arquivo);
				input.close();
				texto.append(new String(arquivo));
			}
			texto.toString();
			
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

	public static Arquivos getInstance() {
		if (unico == null)
			unico = new Arquivos();
		return unico;
	}
	public Qualificadores toQualificadores() {
		return qualificadores;
	}
	
}
