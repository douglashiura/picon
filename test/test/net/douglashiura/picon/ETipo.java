/*
 * Douglas Hiura Longo, 14 de Setembro de 2010.
 * 
 * twitter.com/douglashiura
 * java.inf.ufsc.br/dh
 * douglashiura.blogspot.com
 * douglashiura.parprimo.com
 * douglashiura@gmail.com
 * */
package test.net.douglashiura.picon;

import java.util.List;

public class ETipo {
	private Entidade entidade;
	private List<Entidade> entidades;

	public ETipo() {
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public Entidade getEntidade() {
		return entidade;
	}

	public void setEntidades(List<Entidade> entidades) {
		this.entidades = entidades;
	}

	public List<Entidade> getEntidades() {
		return entidades;
	}

}
