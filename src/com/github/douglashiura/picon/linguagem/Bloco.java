package com.github.douglashiura.picon.linguagem;

public class Bloco {
	public static String camelCase(String name) {
		String bloco = "";
		boolean sinalNumerico = false;
		boolean sinalUpper = false;

		for (Character n : name.toCharArray()) {
			if (n == '_') {
				bloco += n;
			} else if (Character.isUpperCase(n) && !sinalUpper) {
				bloco += ":" + n;
				sinalUpper = true;
				sinalNumerico = false;
			} else if (Character.isDigit(n) && !sinalNumerico) {
				bloco += ":" + n;
				sinalUpper = false;
				sinalNumerico = true;
			} else if (Character.isUpperCase(n) && sinalUpper) {
				bloco += n;
				sinalNumerico = false;
			} else if (Character.isDigit(n) && sinalNumerico) {
				bloco += n;
				sinalUpper = false;
			} else {
				bloco += n;
				if ((!Character.isUpperCase(n) || !Character.isDigit(n))) {
					sinalNumerico = false;
					sinalUpper = false;
				}
			}
		}
		return bloco.toLowerCase();
	}

}
