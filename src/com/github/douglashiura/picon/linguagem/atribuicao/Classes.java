package com.github.douglashiura.picon.linguagem.atribuicao;

import java.lang.reflect.Field;

public class Classes {

	public static Field getCampo(String campo, Class<?> klass) {
		try {
			return  klass.getDeclaredField(campo);
		} catch (NoSuchFieldException e) {
			return getCampo(campo, klass.getSuperclass());
		}
	}

}
