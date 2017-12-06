/*
 * Douglas Hiura Longo, 12 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package test.net.douglashiura.picon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import net.douglashiura.picon.Reflexao;

public class TesteReflexao extends Assert {
	class T {
		Date data;
		Enum a;
		UUID uuid;
	}

	@Test
	public void criarReflexao() {
		assertNotNull(new Reflexao<Entidade>(Entidade.class));
	}

	@Test
	public void refletirLabelNomeValorString() throws Exception {
		Reflexao<Entidade> refletor = new Reflexao<Entidade>(Entidade.class);
		Entidade douglas = new Entidade();
		refletor.criarPrimitivo("nome", "Douglas Hiura", douglas);
		assertEquals("Douglas Hiura", douglas.getNome());
	}

	@Test
	public void refletirLabelIdadeValorInteger() throws Exception {
		Reflexao<Entidade> refletor = new Reflexao<Entidade>(Entidade.class);
		Entidade douglas = new Entidade();
		refletor.criarPrimitivo("idade", "10", douglas);
		assertEquals(new Integer(10), douglas.getIdade());
	}

	@Test
	public void refletirLabelDataValorData() throws Exception {
		Reflexao<T> refletor = new Reflexao<TesteReflexao.T>(T.class);
		T t = new T();
		refletor.criarPrimitivo("data", "2009/10/25 00:00", t);
		assertEquals("2009/10/25", new SimpleDateFormat("yyyy/MM/dd").format(t.data));
	}

	@Test
	public void refletirLabelDataValorDataHora() throws Exception {
		Reflexao<T> refletor = new Reflexao<TesteReflexao.T>(T.class);
		T t = new T();
		refletor.criarPrimitivo("data", "2009/10/25 12:30", t);
		assertEquals("2009/10/25 12:30", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(t.data));
	}

	@Test
	public void refletirLabelDataNow() throws Exception {
		Reflexao<T> refletor = new Reflexao<TesteReflexao.T>(T.class);
		T t = new T();
		refletor.criarPrimitivo("data", "now", t);
		assertEquals(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()),
				new SimpleDateFormat("yyyy/MM/dd HH:mm").format(t.data));
	}

	@Test
	public void refletirEnum() throws Exception {
		Reflexao<T> refletor = new Reflexao<TesteReflexao.T>(T.class);
		T t = new T();
		refletor.criarPrimitivo("a", "A", t);
		assertEquals(Enum.A, t.a);
	}

	@Test(expected = NullPointerException.class)
	public void enumInexistente() throws Exception {
		Reflexao<T> refletor = new Reflexao<TesteReflexao.T>(T.class);
		T t = new T();
		refletor.criarPrimitivo("a", "INEXISTENTE", t);
		assertEquals(Enum.A, t.a);
	}

	@Test
	public void refletirUuid() throws Exception {
		Reflexao<T> refletor = new Reflexao<TesteReflexao.T>(T.class);
		T t = new T();
		UUID uuid = new UUID(1l, 1l);
		refletor.criarPrimitivo("uuid", uuid.toString(), t);
		assertEquals(uuid, t.uuid);
	}

}
