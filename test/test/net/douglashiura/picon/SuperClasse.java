package test.net.douglashiura.picon;

import java.util.UUID;

public abstract class SuperClasse {
	private String superNome;
	private UUID superUuid;
	private Entidade superEntidade;

	public UUID getSuperUuid() {
		return superUuid;
	}

	public String getSuperNome() {
		return superNome;
	}

	public void setSuperUuid(UUID superUuid) {
		this.superUuid = superUuid;
	}

	public Entidade getSuperEntidade() {
		return superEntidade;
	}

}
