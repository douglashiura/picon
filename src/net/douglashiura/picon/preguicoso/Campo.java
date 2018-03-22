/*Douglas Hiura Longo, 18 de Março de 2018.*/

package net.douglashiura.picon.preguicoso;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import net.douglashiura.picon.Tempo;

public class Campo {
	final private static Tempo TEMPO = new Tempo();
	private String valor;
	private String campo;

	public Campo(String campo, String valor) {
		this.campo = campo;
		this.valor = valor;

	}

	public void configure(Object objeto, Contexto contextoPreguisoso) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException, ParseException {
		Field campoDeclarado = objeto.getClass().getDeclaredField(campo);
		campoDeclarado.setAccessible(Boolean.TRUE);
		Class<?> type = campoDeclarado.getType();
		if (type.isEnum()) {
			Object[] enums = type.getEnumConstants();
			for (Object umValor : enums) {
				if (umValor.toString().equals(valor)) {
					campoDeclarado.set(objeto, umValor);
					return;
				}
			}
			throw new NullPointerException("Sem Enum " + campo + " " + valor);
		} else if (type.equals(UUID.class)) {
			campoDeclarado.set(objeto, UUID.fromString(valor));
		} else if (type.equals(Date.class)) {
			campoDeclarado.set(objeto, TEMPO.de(valor));
		} else {
			Constructor<?> construtor = type.getDeclaredConstructor(String.class);
			campoDeclarado.set(objeto, construtor.newInstance(valor));
		}
	}

	public String getCampo() {
		return campo;
	}

	public String getValor() {
		return valor;
	}

}
