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

public class Reflexao {
	private static HashMap<Class<?>, Map<String, Field>> CLASSES = new HashMap<Class<?>, Map<String, Field>>();
	private Map<String, Field> mapaDeAtributos;
	final private static CompiladorDeTempo compile = new CompiladorDeTempo();

	public Reflexao(Class classe) {
		mapaDeAtributos = CLASSES.get(classe);
		if (mapaDeAtributos == null) {
			mapaDeAtributos = new HashMap<String, Field>();
			escanearCampos(classe);
		}
	}

	private void escanearCampos(Class<?> classe) {
		for (Field atributo : classe.getDeclaredFields()) {
			atributo.setAccessible(true);
			mapaDeAtributos.put(atributo.getName(), atributo);
		}
		if (null != classe.getSuperclass())
			escanearCampos(classe.getSuperclass());
	}

	public void criarPrimitivo(String atributo, Object valor, Object umObjeto) throws Exception {
		Field campo = mapaDeAtributos.get(atributo);
		if (campo.getType().equals(Date.class)) {
			try {
				campo.set(umObjeto, compile.compile(valor.toString()));
			} catch (ParseException e) {
				throw new NoSuchFieldException(valor.toString());
			}
		} else if (campo.getType().isEnum()) {
			Object[] enums = campo.getType().getEnumConstants();
			for (Object umValor : enums) {
				if (umValor.toString().equals(valor)) {
					campo.set(umObjeto, umValor);
					return;
				}
			}
			throw new NullPointerException("Sem Enum" + campo.getName() + " " + valor);
		} else if (campo.getType().equals(java.util.UUID.class)) {
			campo.set(umObjeto, java.util.UUID.fromString(valor.toString()));
		} else {
			Constructor<?> c = campo.getType().getDeclaredConstructor(String.class);
			campo.set(umObjeto, c.newInstance(valor));
		}

	}

	public void fixar(String label, Object value, Object umObjeto) throws Exception {
		Field field = mapaDeAtributos.get(label);
		field.set(umObjeto, value);
	}

	@SuppressWarnings("unchecked")
	public Class<Object> getClasse(String label) {
		return (Class<Object>) mapaDeAtributos.get(label).getType();
	}

}
