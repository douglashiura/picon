/*
 * Douglas Hiura Longo, 14 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package net.douglashiura.picon;

import java.util.Map;

import net.douglashiura.picon.linguagem.Parte;

public interface Vinculo {
	void processar(Map<String, Object> referencias) throws Exception;

	Parte getApelido();
}
