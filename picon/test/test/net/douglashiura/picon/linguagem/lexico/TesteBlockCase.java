package test.net.douglashiura.picon.linguagem.lexico;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.douglashiura.picon.linguagem.BlockCase;

public class TesteBlockCase {

	@Test
	public void camelCassesDelegateObject() throws Exception {
		assertEquals("a", BlockCase.camelCase("a"));
		assertEquals("aB", BlockCase.camelCase("a:b"));
		assertEquals("abA", BlockCase.camelCase("ab:a"));
		assertEquals("abAa", BlockCase.camelCase("ab:aa"));
		assertEquals("abAaa", BlockCase.camelCase("ab:aaa"));
		assertEquals("ab1", BlockCase.camelCase("ab:1"));
		assertEquals("ab12", BlockCase.camelCase("ab:12"));
		assertEquals("ab12A", BlockCase.camelCase("ab:12:a"));
		assertEquals("ab12Aaa", BlockCase.camelCase("ab:12:aaa"));
		assertEquals("ab12A1", BlockCase.camelCase("ab:12:a:1"));
		assertEquals("ab12AaAa", BlockCase.camelCase("ab:12:aa:aa"));
		assertEquals("ab12AaAa1a", BlockCase.camelCase("ab:12:aa:aa:1a"));
		assertEquals("poloJaraguaAno2012Periodo2DisciplinaP111", BlockCase.camelCase("polo:jaragua:ano:2012:periodo:2:disciplina:p:111"));
		assertEquals("poloJaraguaAno2012Periodo2DisciplinaPortugues111", BlockCase.camelCase("polo:jaragua:ano:2012:periodo:2:disciplina:portugues:111"));
		assertEquals("poloJaraguaAno2012Periodo2DisciplinaPORT111", BlockCase.camelCase("polo:jaragua:ano:2012:periodo:2:disciplina:port:111"));
		assertEquals("agronomia2012_2", BlockCase.camelCase("agronomia:2012_2"));

	}

}
