package com.github.douglashiura.picon.linguagem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.github.douglashiura.picon.Picon;
import com.github.douglashiura.picon.ProblemaDeCompilacaoException;

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
		StringBuffer texto = new StringBuffer();
		List<URL> arquivos = listarArquivos();
		for (URL url : arquivos) {
			try (InputStream fluxo = url.openStream()) {
				byte[] arquivo = new byte[fluxo.available()];
				fluxo.read(arquivo);
				texto.append(new String(arquivo));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		qualificadores = Picon.explodir(Partes.explodir(texto.toString()));
	}

	private List<URL> listarArquivos() {
		String classPath = System.getProperty("java.class.path", ".");
		String[] entradas = classPath.split(System.getProperty("path.separator"));

		Deque<File> arquivos = new ArrayDeque<>();
		for (String entrada : entradas) {
			arquivos.push(new File(entrada));
		}

		List<URL> urls = new ArrayList<>();
		while (!arquivos.isEmpty()) {
			File arquivo = arquivos.pop();
			if (arquivo.isDirectory()) {
				for (File sub : arquivo.listFiles()) {
					arquivos.push(sub);
				}
			} else {
				if (arquivo.getName().toLowerCase().endsWith(EXTENSAO)) {
					try {
						urls.add(arquivo.toURI().toURL());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				} else if (arquivo.getName().toLowerCase().endsWith(".jar")) {
					try (ZipFile arquivoJar = new ZipFile(arquivo)) {
						Enumeration<? extends ZipEntry> e = arquivoJar.entries();
						while (e.hasMoreElements()) {
							ZipEntry entradaJar = e.nextElement();
							String nome = entradaJar.getName();
							if (nome.toLowerCase().endsWith(EXTENSAO)) {
								URL url = ClassLoader.getSystemResource(nome);
								if (url != null) {
									urls.add(url);
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return urls;
	}

	public static Arquivos getInstance() throws ProblemaDeCompilacaoException {
		return UNICO = UNICO == null ? new Arquivos() : UNICO;
	}

	public Qualificadores explodir() {
		return qualificadores;
	}

}
