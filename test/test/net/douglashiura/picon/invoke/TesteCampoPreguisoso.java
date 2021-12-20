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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.douglashiura.picon.ProblemaDeCompilacaoException;
import com.github.douglashiura.picon.linguagem.Parte;
import com.github.douglashiura.picon.linguagem.Qualificadores;
import com.github.douglashiura.picon.preguicoso.Campo;
import com.github.douglashiura.picon.preguicoso.CampoReferencia;
import com.github.douglashiura.picon.preguicoso.CampoValor;
import com.github.douglashiura.picon.preguicoso.Contexto;
import com.github.douglashiura.picon.preguicoso.Objeto;

import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.Enum;

public class TesteCampoPreguisoso {
	class Klasse {
		Date data;
		Enum enumerado;
		UUID uuid;
	}

	private Contexto preguisoso;
	private Qualificadores qualificadores;

	@BeforeEach
	public void setUp() {
		qualificadores = new Qualificadores();
		preguisoso = new Contexto(qualificadores);
	}

	@Test
	public void refletirLabelNomeValorString() throws Exception {
		Entidade douglas = new Entidade();
		CampoValor campo = new CampoValor("nome", "Douglas Hiura", null);
		campo.configure(douglas, preguisoso);
		assertEquals("Douglas Hiura", douglas.getNome());
	}

	@Test
	public void campoReferencia() throws Exception {
		Entidade alvo = new Entidade();
		qualificadores.put("douglas", new Objeto<>(Entidade.class, null));
		Campo objeto = new CampoReferencia("entidade", "douglas", null);
		objeto.configure(alvo, preguisoso);
		assertEquals(preguisoso.get("douglas"), alvo.getEntidade());
	}

	@Test
	public void refletirLabelIdadeValorInteger() throws Exception {
		Entidade douglas = new Entidade();
		CampoValor numero = new CampoValor("idade", "10", null);
		numero.configure(douglas, preguisoso);
		assertEquals(Integer.valueOf(10), douglas.getIdade());
	}

	@Test
	public void refletirLabelDataValorData() throws Exception {
		Klasse objeto = new Klasse();
		CampoValor tempo = new CampoValor("data", "2009/10/25 00:00", null);
		tempo.configure(objeto, preguisoso);
		assertEquals("2009/10/25", new SimpleDateFormat("yyyy/MM/dd").format(objeto.data));
	}

	@Test
	public void refletirLabelDataValorDataHora() throws Exception {
		Klasse objeto = new Klasse();
		CampoValor campo = new CampoValor("data", "2009/10/25 12:30", null);
		campo.configure(objeto, preguisoso);
		assertEquals("2009/10/25 12:30", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(objeto.data));
	}

	@Test
	public void refletirLabelDataNow() throws Exception {
		Klasse objeto = new Klasse();
		CampoValor tempo = new CampoValor("data", "now", null);
		tempo.configure(objeto, preguisoso);
		assertEquals(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()),
				new SimpleDateFormat("yyyy/MM/dd HH:mm").format(objeto.data));
	}

	@Test
	public void refletirEnum() throws Exception {
		Klasse objeto = new Klasse();
		CampoValor enumerado = new CampoValor("enumerado", "A", null);
		enumerado.configure(objeto, preguisoso);
		assertEquals(Enum.A, objeto.enumerado);
	}

	@Test
	public void enumInexistente() throws Exception {
		Klasse objeto = new Klasse();
		CampoValor campo = new CampoValor("enumerado", "INEXISTENTE", new Parte("INEXISTENTE", null, 0));
		assertThrows(ProblemaDeCompilacaoException.class, () -> campo.configure(objeto, preguisoso));
	}

	@Test
	public void refletirUuid() throws Exception {
		Klasse objeto = new Klasse();
		UUID uuid = new UUID(1l, 1l);
		CampoValor campo = new CampoValor("uuid", uuid.toString(), null);
		campo.configure(objeto, preguisoso);
		assertEquals(uuid, objeto.uuid);
	}
}
