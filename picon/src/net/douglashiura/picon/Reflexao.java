/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package net.douglashiura.picon;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Reflexao<T> {
	private static HashMap<Class<?>, Map<String, Field>> HASH_CLASSES = new HashMap<Class<?>, Map<String, Field>>();
	private Map<String, Field> mapaField;
	final private static TimeCompile compile = new TimeCompile();

	public Reflexao(Class<T> class1) {
		mapaField = HASH_CLASSES.get(class1);
		if (mapaField == null) {
			mapaField = new HashMap<String, Field>();
			escanearCampos(class1);
		}
	}

	private void escanearCampos(Class<?> class1) {
		for (Field field : class1.getDeclaredFields()) {
			field.setAccessible(true);
			mapaField.put(field.getName(), field);
		}
		if (null != class1.getSuperclass())
			escanearCampos(class1.getSuperclass());
	}

	public void criarPrimitivo(String label, Object value, T umObjeto) throws Exception {
		Field field = mapaField.get(label);
		if (field.getType().equals(Date.class)) {
			try {
				field.set(umObjeto, compile.compile(value.toString()));
			} catch (ParseException e) {
				throw new NoSuchFieldException(value.toString());
			}
		} else if (field.getType().isEnum()) {
			Object[] enums = field.getType().getEnumConstants();
			for (Object valor : enums) {
				if (valor.toString().equals(value)) {
					field.set(umObjeto, valor);
					return;
				}
			}
			throw new NullPointerException("Sem Enum" + field.getName() + " " + value);
		} else if (field.getType().equals(java.util.UUID.class)) {
			field.set(umObjeto, java.util.UUID.fromString(value.toString()));
		} else {
			Constructor<?> c = field.getType().getDeclaredConstructor(String.class);
			field.set(umObjeto, c.newInstance(value));
		}

	}

	public void fixar(String label, Object value, T umObjeto) throws Exception {
		Field field = mapaField.get(label);
		field.set(umObjeto, value);
	}

	@SuppressWarnings("unchecked")
	public Class<Object> getClasse(String label) {
		return (Class<Object>) mapaField.get(label).getType();
	}

}
