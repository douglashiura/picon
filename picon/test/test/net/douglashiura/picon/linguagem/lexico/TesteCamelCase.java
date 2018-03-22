package test.net.douglashiura.picon.linguagem.lexico;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.douglashiura.picon.linguagem.Bloco;

public class TesteCamelCase {

	@Test
	public void camelCasses() throws Exception {
		assertEquals("a", Bloco.camelCase("a"));
		assertEquals("a:b", Bloco.camelCase("aB"));
		assertEquals("ab:a", Bloco.camelCase("abA"));
		assertEquals("ab:aa", Bloco.camelCase("abAa"));
		assertEquals("ab:aaa", Bloco.camelCase("abAaa"));
		assertEquals("ab:1", Bloco.camelCase("ab1"));
		assertEquals("ab:12", Bloco.camelCase("ab12"));
		assertEquals("ab:12:a", Bloco.camelCase("ab12A"));
		assertEquals("ab:12:aaa", Bloco.camelCase("ab12Aaa"));
		assertEquals("ab:12:a:1", Bloco.camelCase("ab12A1"));
		assertEquals("ab:12:aa:aa", Bloco.camelCase("ab12AaAa"));
		assertEquals("ab:12:aa:aa:1a", Bloco.camelCase("ab12AaAa1a"));
		assertEquals("polo:jaragua:ano:2012:periodo:2:disciplina:p:111",Bloco.camelCase("poloJaraguaAno2012Periodo2DisciplinaP111"));
		assertEquals("polo:jaragua:ano:2012:periodo:2:disciplina:portugues:111",Bloco.camelCase("poloJaraguaAno2012Periodo2DisciplinaPortugues111"));
		assertEquals("polo:jaragua:ano:2012:periodo:2:disciplina:port:111",Bloco.camelCase("poloJaraguaAno2012Periodo2DisciplinaPORT111"));
		assertEquals("agronomia:2012_2", Bloco.camelCase("agronomia2012_2"));
	}

}
