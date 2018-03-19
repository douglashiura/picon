/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package test.net.douglashiura.picon.invoke;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import net.douglashiura.picon.preguicoso.CampoPreguisoso;
import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.Enum;

public class TesteCampoPreguisoso extends Assert {
	class T {
		Date data;
		Enum a;
		UUID uuid;
	}

	@Test
	public void refletirLabelNomeValorString() throws Exception {
		Entidade douglas = new Entidade();
		CampoPreguisoso campo = new CampoPreguisoso("nome", "Douglas Hiura");
		campo.configure(douglas);
		assertEquals("Douglas Hiura", douglas.getNome());
	}

	@Test
	public void refletirLabelIdadeValorInteger() throws Exception {
		Entidade douglas = new Entidade();
		CampoPreguisoso campo = new CampoPreguisoso("idade", "10");
		campo.configure(douglas);
		assertEquals(new Integer(10), douglas.getIdade());
	}

	@Test
	public void refletirLabelDataValorData() throws Exception {
		T t = new T();
		CampoPreguisoso campo = new CampoPreguisoso("data", "2009/10/25 00:00");
		campo.configure(t);
		assertEquals("2009/10/25", new SimpleDateFormat("yyyy/MM/dd").format(t.data));
	}

	@Test
	public void refletirLabelDataValorDataHora() throws Exception {
		T t = new T();
		CampoPreguisoso campo = new CampoPreguisoso("data", "2009/10/25 12:30");
		campo.configure(t);
		assertEquals("2009/10/25 12:30", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(t.data));
	}

	@Test
	public void refletirLabelDataNow() throws Exception {
		T t = new T();
		CampoPreguisoso campo = new CampoPreguisoso("data", "now");
		campo.configure(t);
		assertEquals(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()), new SimpleDateFormat("yyyy/MM/dd HH:mm").format(t.data));
	}

	@Test
	public void refletirEnum() throws Exception {
		T t = new T();
		CampoPreguisoso campo = new CampoPreguisoso("a", "A");
		campo.configure(t);
		assertEquals(Enum.A, t.a);
	}

	@Test(expected = NullPointerException.class)
	public void enumInexistente() throws Exception {
		T t = new T();
		CampoPreguisoso campo = new CampoPreguisoso("a", "INEXISTENTE");
		campo.configure(t);
	}

	@Test
	public void refletirUuid() throws Exception {
		T t = new T();
		UUID uuid = new UUID(1l, 1l);
		CampoPreguisoso campo = new CampoPreguisoso("uuid", uuid.toString());
		campo.configure(t);
		assertEquals(uuid, t.uuid);
	}
}
