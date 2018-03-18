package test.net.douglashiura.picon.pregicoso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.picon.preguicoso.CampoPreguisoso;
import net.douglashiura.picon.preguicoso.ContextoPreguisoso;
import net.douglashiura.picon.preguicoso.ObjetoPreguicoso;
import net.douglashiura.picon.preguicoso.ParametroPreguicoso;
import net.douglashiura.picon.preguicoso.ParametroRefereciaPreguicoso;
import net.douglashiura.picon.preguicoso.Qualificadores;
import test.net.douglashiura.picon.Entidade;
import test.net.douglashiura.picon.EntidadeComConstrutor;

public class TesteObjetoPreguicoso {

	private ObjetoPreguicoso<Entidade> objetoEntidade;
	private ContextoPreguisoso contexto;

	@Before
	public void setUp() {
		objetoEntidade = new ObjetoPreguicoso<>(Entidade.class);

	}

	@Test
	public void instanciar() throws Exception {
		assertNotNull(objetoEntidade.instanciar(contexto));
		assertNotSame(objetoEntidade.instanciar(contexto), objetoEntidade.instanciar(contexto));
	}

	@Test
	public void instanciarComConstrutor() throws Exception {
		ObjetoPreguicoso<EntidadeComConstrutor> objetoEntidadeComConstrutor = new ObjetoPreguicoso<>(
				EntidadeComConstrutor.class);
		objetoEntidadeComConstrutor.adicionarParametro(new ParametroPreguicoso("Douglas"));
		EntidadeComConstrutor objeto = objetoEntidadeComConstrutor.instanciar(contexto);
		assertEquals("Douglas", objeto.obterNome());
	}

	@Test
	public void instanciarComConstrutorComDoisParametros() throws Exception {
		Qualificadores qualificadores = new Qualificadores();
		ObjetoPreguicoso<EntidadeComConstrutor> objetoPedro = new ObjetoPreguicoso<>(EntidadeComConstrutor.class);
		qualificadores.put("pedro", objetoPedro);
		contexto = new ContextoPreguisoso(qualificadores);
		ObjetoPreguicoso<EntidadeComConstrutor> objetoEntidadeComConstrutor = new ObjetoPreguicoso<>(
				EntidadeComConstrutor.class);
		objetoEntidadeComConstrutor.adicionarParametro(new ParametroPreguicoso("Douglas"));
		objetoEntidadeComConstrutor.adicionarParametro(new ParametroRefereciaPreguicoso("pedro"));
		EntidadeComConstrutor objeto = objetoEntidadeComConstrutor.instanciar(contexto);
		assertEquals("Douglas", objeto.obterNome());
		assertEquals(contexto.get("pedro"), objeto.obterPedro());
	}

	@Test
	public void campoNome() throws Exception {
		objetoEntidade.adicionar(new CampoPreguisoso("nome", "Douglas"));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals("Douglas", objeto.getNome());
	}

	@Test
	public void campoIdade() throws Exception {
		objetoEntidade.adicionar(new CampoPreguisoso("idade", "1"));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals(new Integer(1), objeto.getIdade());
	}

	@Test
	public void camposNomeEIdade() throws Exception {
		objetoEntidade.adicionar(new CampoPreguisoso("nome", "Douglas"));
		objetoEntidade.adicionar(new CampoPreguisoso("idade", "1"));
		Entidade objeto = objetoEntidade.instanciar(contexto);
		assertEquals("Douglas", objeto.getNome());
		assertEquals(new Integer(1), objeto.getIdade());
	}
}