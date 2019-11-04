package net.douglashiura.picon.linguagem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import net.douglashiura.picon.Picon;
import net.douglashiura.picon.ProblemaDeCompilacaoException;

public class Arquivos {

	private static final String EXTENSAO = ".picon";
	private static Arquivos UNICO;
	private Qualificadores qualificadores;

	private Arquivos() throws ProblemaDeCompilacaoException {
		System.out.println(
				"Licença do Picon - está versão é grátis para desenvolvimento de software livre ou não comercial.\n"
						+ "Para uso durante o desenvolvimento de software comercial contate\no Laboratório de Engenharia de Software e Banco de Dados situado na \n"
						+ "Universidade Federal de Santa Catarina\n" + "Campus Universitário\n" + "INE – Sala 308\n"
						+ "Florianópolis, Santa Catarina, Brasil\n" + "Telefone: +55 (48) 3721-4712");
		try {
			Arquivos.class.getClassLoader();
			Enumeration<URL> recursos = ClassLoader.getSystemResources("");
			List<File> arquivos = new ArrayList<File>();
			while (recursos.hasMoreElements()) {
				URL url = (URL) recursos.nextElement();
				ler(new File(url.getFile()), arquivos);
			}
			StringBuffer texto = new StringBuffer();
			for (File filos : arquivos) {
				InputStream fluxo = new FileInputStream(filos);
				byte[] arquivo = new byte[fluxo.available()];
				fluxo.read(arquivo);
				fluxo.close();
				texto.append(new String(arquivo));
			}
			qualificadores = Picon.explodir(Partes.explodir(texto.toString()));
		} catch (IOException e) {
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

	public static Arquivos getInstance() throws ProblemaDeCompilacaoException {
		return UNICO = UNICO == null ? new Arquivos() : UNICO;
	}

	public Qualificadores explodir() {
		return qualificadores;
	}

}
