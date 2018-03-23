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
			Enumeration<URL> recursos = ClassLoader.getSystemResources("");
			List<File> arquivos = new ArrayList<File>();
			while (recursos.hasMoreElements()) {
				URL url = (URL) recursos.nextElement();
				ler(new File(url.toURI()), arquivos);
			}
			StringBuffer texto = new StringBuffer();
			for (File filos : arquivos) {
				InputStream fluxo = new FileInputStream(filos);
				byte[] arquivo = new byte[fluxo.available()];
				fluxo.read(arquivo);
				fluxo.close();
				texto.append(new String(arquivo));
			}			
			qualificadores=Picon.explodir(Partes.explodir(texto.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void ler(File arquivo, List<File> arquivos) {
		if (arquivo.isFile() && arquivo.getName().endsWith(EXTENSAO))
			arquivos.add(arquivo);
		if (arquivo.isDirectory())
			for (File filho : arquivo.listFiles())
				ler(filho, arquivos);
	}

	public static Arquivos getInstance() {
		if (unico == null)
			unico = new Arquivos();
		return unico;
	}
	public Qualificadores explodir() {
		return qualificadores;
	}
	
}
