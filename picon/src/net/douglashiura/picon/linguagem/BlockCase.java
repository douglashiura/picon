package net.douglashiura.picon.linguagem;

public class BlockCase {
	public static String camelCase(String name) {
		String camelCase = "";
		boolean sinalNumerico = false;
		boolean sinalUpper = false;

		for (Character n : name.toCharArray()) {
			if (n == '_') {
				camelCase += n;
			} else if (Character.isUpperCase(n) && !sinalUpper) {
				camelCase += ":" + n;
				sinalUpper = true;
				sinalNumerico = false;
			} else if (Character.isDigit(n) && !sinalNumerico) {
				camelCase += ":" + n;
				sinalUpper = false;
				sinalNumerico = true;
			} else if (Character.isUpperCase(n) && sinalUpper) {
				camelCase += n;
				sinalNumerico = false;
			} else if (Character.isDigit(n) && sinalNumerico) {
				camelCase += n;
				sinalUpper = false;
			} else {
				camelCase += n;
				if ((!Character.isUpperCase(n) || !Character.isDigit(n))) {
					sinalNumerico = false;
					sinalUpper = false;
				}
			}
		}
		return camelCase.toLowerCase();

	}
}
