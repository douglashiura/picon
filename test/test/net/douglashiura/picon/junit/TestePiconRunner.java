package test.net.douglashiura.picon.junit;

import static net.douglashiura.picon.junit.PiconRunner.get;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import net.douglashiura.picon.junit.PiconRunner;
import net.douglashiura.picon.preguicoso.Contexto;
import test.net.douglashiura.picon.ETipo;

public class TestePiconRunner {

	private ETipo semDeclaracaoNaON;
	private ETipo injetadaPeloPicon;
	private ETipo invodadaPelaClasse = new ETipo();
	private ETipo semDeclaracaoEqualb = invodadaPelaClasse;
	private ETipo semDeclaracaoEqualSemDeclaracaoEqualb = semDeclaracaoEqualb;

	@Test
	public void semDeclaracaoNaOBjectNotation() {
		assertNull(semDeclaracaoNaON);

	}

	@Test
	public void aDeclarado() {
		assertNotNull(injetadaPeloPicon);
	}

	@Test
	public void delegateShareObject() {
		assertNotNull(get("injetada:pelo:picon"));
		assertNotNull(get("b"));
	}

	@Test
	public void bDeclaradoInstanciado() {
		assertFalse(invodadaPelaClasse.equals(get("b")));
	}

	@Test
	public void bDeclaradoComModeloInstanciado() {
		assertFalse(invodadaPelaClasse.equals(get("b")));
	}

	@Test
	public void montarOutroContexto() throws Exception {
		assertSame(injetadaPeloPicon, get("injetada:pelo:picon"));
		Contexto outro = PiconRunner.build();
		assertNotSame(injetadaPeloPicon, outro.get("injetada:pelo:picon"));
		assertSame(injetadaPeloPicon, get("injetada:pelo:picon"));

	}

	@Test
	public void _estadosDeInvocacao() throws Exception {
		ETipo injetadaPeloPicon = get("injetada:pelo:picon");
		assertNotNull(injetadaPeloPicon);
		assertEquals(injetadaPeloPicon, injetadaPeloPicon);
		assertNull(semDeclaracaoNaON);
		assertNotNull(injetadaPeloPicon);
		assertNotNull(invodadaPelaClasse);
		assertNotSame(invodadaPelaClasse,get("b"));
		assertEquals(semDeclaracaoEqualb, invodadaPelaClasse);
		assertEquals(semDeclaracaoEqualSemDeclaracaoEqualb, invodadaPelaClasse);

	}

}
