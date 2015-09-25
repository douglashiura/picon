package test.net.douglashiura.picon;

import static net.douglashiura.picon.junit.PiconRunner.get;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import net.douglashiura.picon.ExceptionCompilacao;
import net.douglashiura.picon.PiconStore;
import net.douglashiura.picon.junit.PiconRunner;

public class TesteTestePiconRunner {

	private ETipo semDeclaracaoNaON;
	private ETipo a;
	private ETipo b = new ETipo();
	private ETipo semDeclaracaoEqualb = b;
	private ETipo semDeclaracaoEqualSemDeclaracaoEqualb = semDeclaracaoEqualb;

	@Test
	public void semDeclaracaoNaOBjectNotation() {
		assertNull(semDeclaracaoNaON);

	}

	@Test
	public void aDeclarado() {
		assertNotNull(a);
	}

	@Test
	public void delegateShareObject() {
		assertNotNull(get("a"));
		assertNotNull(get("b"));
	}

	@Test
	public void bDeclaradoInstanciado() {
		assertFalse(b.equals(get("b")));
	}

	@Test
	public void bDeclaradoComModeloInstanciado() {
		assertFalse(b.equals(get("b", ETipo.class)));
	}

	@Test
	public void montarOutroContexto() throws IOException, ExceptionCompilacao {
		assertTrue(a.equals(get("a", ETipo.class)));
		assertTrue(a.equals(get("a")));
		PiconStore outro = PiconRunner.build();
		assertFalse(a.equals(outro.get("a", ETipo.class)));
		assertFalse(a.equals(outro.get("a")));
		assertTrue(a.equals(get("a", ETipo.class)));
		assertTrue(a.equals(get("a")));
	}

	@Test
	public void _estadosDeInvocacao() throws Exception {
		ETipo a = get("a");
		assertNotNull(a);
		assertEquals(a, a);
		assertNull(semDeclaracaoNaON);
		assertNotNull(a);
		assertNotNull(b);
		assertFalse(b.equals(get("b")));
		assertEquals(semDeclaracaoEqualb, b);
		assertEquals(semDeclaracaoEqualSemDeclaracaoEqualb, b);

	}

}
