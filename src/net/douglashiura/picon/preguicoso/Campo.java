package net.douglashiura.picon.preguicoso;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Parte;
import net.douglashiura.picon.linguagem.atribuicao.Classes;

public abstract class Campo {
	private String valor;
	private String campo;
	private Parte parte;

	public Campo(String campo, String valor, Parte parte) {
		this.campo = campo;
		this.valor = valor;
		this.parte = parte;
	}

	final public void configure(Object objeto, Contexto contexto)
			throws SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException,
			InstantiationException, InvocationTargetException, ParseException, ProblemaDeCompilacaoException {
		try {
			Field campoDeclarado = Classes.getCampo(campo, objeto.getClass());
			campoDeclarado.setAccessible(Boolean.TRUE);
			Class<?> type = campoDeclarado.getType();
			campoDeclarado.set(objeto, valor(type, contexto));
		} catch (NullPointerException semCampo) {
			throw new ProblemaDeCompilacaoException(semCampo, parte);
		}

	}

	abstract Object valor(Class<?> type, Contexto contexto)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			ParseException, NoSuchMethodException, SecurityException, ProblemaDeCompilacaoException;

	public String getCampo() {
		return campo;
	}

	public String getValor() {
		return valor;
	}

}
