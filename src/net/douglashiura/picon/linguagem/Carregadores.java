package net.douglashiura.picon.linguagem;

import java.net.URLClassLoader;

public class Carregadores {

	public static Class<?> buscarClasse(String nomeDaKlasse) throws ClassNotFoundException {
		for (ClassLoader carregador : getCarregadores()) {
			try {
				return carregar(nomeDaKlasse, carregador);
			} catch (ClassNotFoundException semClasse) {
			}
		}
		throw new ClassNotFoundException();
	}

	private static Class<?> carregar(String nome, ClassLoader load) throws ClassNotFoundException {
		return load.loadClass(nome);
	}

	private static ClassLoader[] carregadores;

	private static ClassLoader[] getCarregadores() {
		return carregadores = carregadores == null ? possibiliades() : carregadores;
	}

	private static ClassLoader[] possibiliades() {
		return new ClassLoader[] { Thread.currentThread().getContextClassLoader(), ClassLoader.getSystemClassLoader(),
				URLClassLoader.getSystemClassLoader() };
	}

}
