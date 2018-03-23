package test.net.douglashiura.picon.junit;

import static net.douglashiura.picon.junit.PiconRunner.get;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import test.net.douglashiura.picon.ETipo;

public class TestePiconRunnerCamelCase {

	private ETipo a;
	private ETipo aB;
	private ETipo abA;
	private ETipo abAa;
	private ETipo abAaa;
	private ETipo ab1;
	private ETipo ab12;
	private ETipo ab12A;
	private ETipo ab12Aaa;
	private ETipo ab12A1;
	private ETipo ab12AaAa;
	private ETipo ab12AaAa1a;
	private ETipo poloJaraguaAno2012Periodo2DisciplinaP111;
	private ETipo poloJaraguaAno2012Periodo2DisciplinaPortugues111;
	private ETipo poloJaraguaAno2012Periodo2DisciplinaPORT111;
	private ETipo agronomia2012_2;

	@Test
	public void camelCasses() throws Exception {
		assertNotNull(a);
		assertNotNull(aB);
		assertNotNull(abA);
		assertNotNull(abAa);
		assertNotNull(abAaa);
		assertNotNull(ab1);
		assertNotNull(ab12);
		assertNotNull(ab12A);
		assertNotNull(ab12Aaa);
		assertNotNull(ab12A1);
		assertNotNull(ab12AaAa);
		assertNotNull(ab12AaAa1a);
		assertNotNull(poloJaraguaAno2012Periodo2DisciplinaP111);
		assertNotNull(poloJaraguaAno2012Periodo2DisciplinaPortugues111);
		assertNotNull(poloJaraguaAno2012Periodo2DisciplinaPORT111);
		assertNotNull(agronomia2012_2);

	}

	@Test
	public void camelCassesDelegateObject() throws Exception {
		assertSame(a, get("a"));
		assertSame(aB, get("a:b"));
		assertSame(abA, get("ab:a"));
		assertSame(abAa, get("ab:aa"));
		assertSame(abAaa, get("ab:aaa"));
		assertSame(ab1, get("ab:1"));
		assertSame(ab12, get("ab:12"));
		assertSame(ab12A, get("ab:12:a"));
		assertSame(ab12Aaa, get("ab:12:aaa"));
		assertSame(ab12A1, get("ab:12:a:1"));
		assertSame(ab12AaAa, get("ab:12:aa:aa"));
		assertSame(ab12AaAa1a, get("ab:12:aa:aa:1a"));
		assertSame(poloJaraguaAno2012Periodo2DisciplinaP111, get("polo:jaragua:ano:2012:periodo:2:disciplina:p:111"));
		assertSame(poloJaraguaAno2012Periodo2DisciplinaPortugues111,
				get("polo:jaragua:ano:2012:periodo:2:disciplina:portugues:111"));
		assertSame(poloJaraguaAno2012Periodo2DisciplinaPORT111,
				get("polo:jaragua:ano:2012:periodo:2:disciplina:port:111"));
		assertSame(agronomia2012_2, get("agronomia:2012_2"));

	}

}
