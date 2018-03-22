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

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.linguagem.Qualificadores;
import net.douglashiura.picon.preguicoso.Campo;
import net.douglashiura.picon.preguicoso.CampoReferencia;
import net.douglashiura.picon.preguicoso.Contexto;
import net.douglashiura.picon.preguicoso.Objeto;
import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.Enum;

public class TesteCampoPreguisoso {
	class Klasse {
		Date data;
		Enum enumerado;
		UUID uuid;
	}

	private Contexto contextoPreguisoso;
	private Qualificadores qualificadores;

	@Before
	public void setUp() {
		qualificadores = new Qualificadores();
		contextoPreguisoso = new Contexto(qualificadores);
	}

	@Test
	public void refletirLabelNomeValorString() throws Exception {
		Entidade douglas = new Entidade();
		Campo campo = new Campo("nome", "Douglas Hiura");
		campo.configure(douglas, contextoPreguisoso);
		assertEquals("Douglas Hiura", douglas.getNome());
	}

	@Test
	public void campoReferencia() throws Exception {
		Entidade alvo = new Entidade();
		qualificadores.put("douglas", new Objeto<>(Entidade.class));
		Campo objeto = new CampoReferencia("entidade", "douglas");
		objeto.configure(alvo, contextoPreguisoso);
		assertEquals(contextoPreguisoso.get("douglas"), alvo.getEntidade());
	}

	@Test
	public void refletirLabelIdadeValorInteger() throws Exception {
		Entidade douglas = new Entidade();
		Campo numero = new Campo("idade", "10");
		numero.configure(douglas, contextoPreguisoso);
		assertEquals(new Integer(10), douglas.getIdade());
	}

	@Test
	public void refletirLabelDataValorData() throws Exception {
		Klasse objeto = new Klasse();
		Campo tempo = new Campo("data", "2009/10/25 00:00");
		tempo.configure(objeto, contextoPreguisoso);
		assertEquals("2009/10/25", new SimpleDateFormat("yyyy/MM/dd").format(objeto.data));
	}

	@Test
	public void refletirLabelDataValorDataHora() throws Exception {
		Klasse objeto = new Klasse();
		Campo campo = new Campo("data", "2009/10/25 12:30");
		campo.configure(objeto, contextoPreguisoso);
		assertEquals("2009/10/25 12:30", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(objeto.data));
	}

	@Test
	public void refletirLabelDataNow() throws Exception {
		Klasse objeto = new Klasse();
		Campo tempo = new Campo("data", "now");
		tempo.configure(objeto, contextoPreguisoso);
		assertEquals(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()), new SimpleDateFormat("yyyy/MM/dd HH:mm").format(objeto.data));
	}

	@Test
	public void refletirEnum() throws Exception {
		Klasse objeto = new Klasse();
		Campo enumerado = new Campo("enumerado", "A");
		enumerado.configure(objeto, contextoPreguisoso);
		assertEquals(Enum.A, objeto.enumerado);
	}

	@Test(expected = NullPointerException.class)
	public void enumInexistente() throws Exception {
		Klasse objeto = new Klasse();
		Campo campo = new Campo("enumerado", "INEXISTENTE");
		campo.configure(objeto, contextoPreguisoso);
	}

	@Test
	public void refletirUuid() throws Exception {
		Klasse objeto = new Klasse();
		UUID uuid = new UUID(1l, 1l);
		Campo campo = new Campo("uuid", uuid.toString());
		campo.configure(objeto, contextoPreguisoso);
		assertEquals(uuid, objeto.uuid);
	}
}
