package test.net.douglashiura.picon.junit;

import static net.douglashiura.picon.junit.PiconRunner.get;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.douglashiura.picon.junit.PiconRunner;
import net.douglashiura.picon.preguicoso.ContextoPreguisoso;
import test.net.douglashiura.picon.ETipo;

public class TestePiconRunner {

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
		assertFalse(b.equals(get("b")));
	}

	@Test
	public void montarOutroContexto() throws Exception {
		assertTrue(a.equals(get("a")));
		assertTrue(a.equals(get("a")));
		ContextoPreguisoso outro = PiconRunner.build();
		assertFalse(a.equals(outro.get("a")));
		assertFalse(a.equals(outro.get("a")));
		assertTrue(a.equals(get("a")));
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
