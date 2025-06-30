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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Entidade extends SuperClasse {
	private String nome;
	private Integer idade;
	private Entidade entidade;
	private List<Entidade> entidades;
	private UUID uuid;
	private List<Enum> enums;
	private List<String> strings;
	private Date natal;
	private Set<Entidade> conjunto;
	private LocalDateTime createdAt;
	private LocalDate birthdate;
	private OffsetDateTime offsetDateTime;
	private OffsetTime offsetTime;
	private Instant instant;
	private YearMonth expiration;

	public Instant getInstant() {
		return instant;
	}

	public YearMonth getExpiration() {
		return expiration;
	}

	public OffsetTime getOffsetTime() {
		return offsetTime;
	}

	public OffsetDateTime getOffsetDateTime() {
		return offsetDateTime;
	}

	public List<Enum> getEnums() {
		return enums;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
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

	public Date getNatal() {
		return natal;
	}

	public Set<Entidade> getConjunto() {
		return conjunto;
	}
}
