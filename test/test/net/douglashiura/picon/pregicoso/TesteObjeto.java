package test.net.douglashiura.picon.pregicoso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;

import com.github.douglashiura.picon.linguagem.Qualificadores;
import com.github.douglashiura.picon.linguagem.atribuicao.lista.Estrategia;
import com.github.douglashiura.picon.linguagem.atribuicao.lista.EstrategiaReferencia;
import com.github.douglashiura.picon.linguagem.atribuicao.lista.EstrategiaValor;
import com.github.douglashiura.picon.preguicoso.CampoReferencia;
import com.github.douglashiura.picon.preguicoso.CampoReferenciaLista;
import com.github.douglashiura.picon.preguicoso.CampoValor;
import com.github.douglashiura.picon.preguicoso.Contexto;
import com.github.douglashiura.picon.preguicoso.Objeto;
import com.github.douglashiura.picon.preguicoso.ParametroReferecia;
import com.github.douglashiura.picon.preguicoso.ParametroValor;

import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.EntidadeComConstrutor;

public class TesteObjeto {

	private Objeto<Entidade> objetoEntidade;
	private Contexto contexto;
	private Qualificadores qualificadores;

	@Before
	public void setUp() {
		objetoEntidade = new Objeto<>(Entidade.class, null);
		qualificadores = new Qualificadores();
	}

	@Test
	public void instanciar() throws Exception {
		assertNotNull(objetoEntidade.instanciar(contexto));
		assertNotSame(objetoEntidade.instanciar(contexto), objetoEntidade.instanciar(contexto));
	}

	@Test
	public void instanciarComConstrutor() throws Exception {
		Objeto<EntidadeComConstrutor> objetoEntidadeComConstrutor = new Objeto<>(EntidadeComConstrutor.class, null);
		objetoEntidadeComConstrutor.adicionarParametro(new ParametroValor("Douglas"));
		EntidadeComConstrutor objeto = objetoEntidadeComConstrutor.instanciar(contexto);
		assertEquals("Douglas", objeto.obterNome());
	}

	@Test
	public void instanciarComConstrutorComDoisParametros() throws Exception {
		qualificadores.put("pedro", new Objeto<>(EntidadeComConstrutor.class, null));
		contexto = new Contexto(qualificadores);
		Objeto<EntidadeComConstrutor> objetoEntidadeComConstrutor = new Objeto<>(EntidadeComConstrutor.class, null);
		objetoEntidadeComConstrutor.adicionarParametro(new ParametroValor("Douglas"));
		objetoEntidadeComConstrutor.adicionarParametro(new ParametroReferecia("pedro"));
		EntidadeComConstrutor objeto = objetoEntidadeComConstrutor.instanciar(contexto);
		assertEquals("Douglas", objeto.obterNome());
		assertEquals(contexto.get("pedro"), objeto.obterPedro());
	}

	@Test
	public void campoNome() throws Exception {
		objetoEntidade.adicionar(new CampoValor("nome", "Douglas", null));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals("Douglas", objeto.getNome());
	}

	@Test
	public void campoIdade() throws Exception {
		objetoEntidade.adicionar(new CampoValor("idade", "1", null));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals(Integer.valueOf(1), objeto.getIdade());
	}

	@Test
	public void campoReferencia() throws Exception {
		qualificadores.put("douglas", new Objeto<>(Entidade.class, null));
		contexto = new Contexto(qualificadores);
		objetoEntidade.adicionar(new CampoReferencia("entidade", "douglas", null));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals(contexto.get("douglas"), objeto.getEntidade());
		assertNotNull(objeto.getEntidade());
	}

	@Test
	public void campoReferenciaSuperClasse() throws Exception {
		qualificadores.put("douglas", new Objeto<>(Entidade.class, null));
		contexto = new Contexto(qualificadores);
		objetoEntidade.adicionar(new CampoReferencia("superEntidade", "douglas", null));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals(contexto.get("douglas"), objeto.getSuperEntidade());
	}

	@Test
	public void campoListaReferencia() throws Exception {
		qualificadores.put("douglas", new Objeto<>(Entidade.class, null));
		contexto = new Contexto(qualificadores);
		Estrategia estrategia = new EstrategiaReferencia(qualificadores, Entidade.class);
		estrategia.adicionar("douglas");
		objetoEntidade.adicionar(new CampoReferenciaLista("entidades", estrategia, null));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals(1, objeto.getEntidades().size());
		assertEquals(contexto.get("douglas"), objeto.getEntidades().get(0));
	}

	@Test
	public void campoConjuntoReferencia() throws Exception {
		qualificadores.put("douglas", new Objeto<>(Entidade.class, null));
		contexto = new Contexto(qualificadores);
		Estrategia estrategia = new EstrategiaReferencia(qualificadores, Entidade.class);
		estrategia.adicionar("douglas");
		objetoEntidade.adicionar(new CampoReferenciaLista("conjunto", estrategia, null));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals(1, objeto.getConjunto().size());
		assertEquals(contexto.get("douglas"), objeto.getConjunto().iterator().next());
	}

	@Test
	public void campoListaValorString() throws Exception {
		qualificadores.put("douglas", new Objeto<>(Entidade.class, null));
		contexto = new Contexto(qualificadores);
		EstrategiaValor estrategia = new EstrategiaValor(String.class, null);
		estrategia.adicionar("Douglas");
		objetoEntidade.adicionar(new CampoReferenciaLista("strings", estrategia, null));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals(1, objeto.getStrings().size());
		assertEquals("Douglas", objeto.getStrings().get(0));
	}

	@Test
	public void campoListaValorEnumerados() throws Exception {
		qualificadores.put("douglas", new Objeto<>(Entidade.class, null));
		contexto = new Contexto(qualificadores);
		EstrategiaValor estrategia = new EstrategiaValor(test.net.douglashiura.picon.Enum.class, null);
		estrategia.adicionar("A");
		objetoEntidade.adicionar(new CampoReferenciaLista("enums", estrategia, null));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals(1, objeto.getEnums().size());
		assertEquals(test.net.douglashiura.picon.Enum.A, objeto.getEnums().get(0));
	}

	@Test
	public void camposNomeEIdade() throws Exception {
		objetoEntidade.adicionar(new CampoValor("nome", "Douglas", null));
		objetoEntidade.adicionar(new CampoValor("idade", "1", null));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals("Douglas", objeto.getNome());
		assertEquals(Integer.valueOf(1), objeto.getIdade());
	}
}