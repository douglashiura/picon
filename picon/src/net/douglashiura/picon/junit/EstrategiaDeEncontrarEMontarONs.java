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

class EstrategiaDeEncontrarEMontarONs {

	final private static EstrategiaDeEncontrarEMontarONs UNICO = new EstrategiaDeEncontrarEMontarONs();

	private String O_N = "";

	private EstrategiaDeEncontrarEMontarONs() {
		try {
			EstrategiaDeEncontrarEMontarONs.class.getClassLoader();
			Enumeration<URL> resources = ClassLoader.getSystemResources("");
			List<File> piconolos = new ArrayList<File>();
			while (resources.hasMoreElements()) {
				URL url = (URL) resources.nextElement();
				ler(new File(url.toURI()), piconolos);
			}
			
			StringBuffer all = new StringBuffer();
			for (File picoloszinho : piconolos) {
				InputStream input = new FileInputStream(picoloszinho);
				byte[] arquivo = new byte[input.available()];
				input.read(arquivo);
				input.close();
				all.append(new String(arquivo));
			}
			O_N = all.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void ler(File file, List<File> picons) {
		if (file.isFile() && file.getName().endsWith(".picon"))
			picons.add(file);
		if (file.isDirectory())
			for (File filho : file.listFiles())
				ler(filho, picons);
	}

	public static EstrategiaDeEncontrarEMontarONs getInstance() {
		return UNICO;
	}

	@Override
	public String toString() {
		return O_N;
	}

}
