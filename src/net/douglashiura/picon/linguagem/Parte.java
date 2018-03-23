/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package net.douglashiura.picon.linguagem;

public class Parte {
	private String valor;
	private Parte anterior;
	private int linha;

	public Parte(String valor, Parte anterior, int linha) {
		this.valor = valor;
		this.anterior = anterior;
		this.linha = linha;
	}

	public String valor() {
		return valor;
	}

	public Parte getAnterior() {
		return anterior;
	}

	public int getLinha() {
		return linha;
	}

}
