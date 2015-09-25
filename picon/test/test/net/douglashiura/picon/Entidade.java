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

import java.util.List;
import java.util.UUID;

public class Entidade extends SuperClass {
	private String nome;
	private Integer idade;
	private Entidade entidade;
	private List<Entidade> entidades;
	private UUID uuid;
	private List<Enum> enums;
	private List<String> strings;

	public List<Enum> getEnums() {
		return enums;
	}

	public String getNome() {
		return nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public Entidade getEntidade() {
		return entidade;
	}

	public List<Entidade> getEntidades() {
		return entidades;
	}

	public UUID getUuid() {
		return uuid;
	}

	public List<String> getStrings() {

		return strings;
	}

}
