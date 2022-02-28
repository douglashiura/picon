package test.net.douglashiura.picon;

public class EntidadeComConstrutor {
	private EntidadeComConstrutor pedro;
	private String nome;
	private Long numero;

	public EntidadeComConstrutor(EntidadeComConstrutor comConstrutor) {
		this.pedro = comConstrutor;
	}

	public EntidadeComConstrutor() {
	}

	public EntidadeComConstrutor(String nome) {
		this.nome = nome;
	}

	public EntidadeComConstrutor(String nome, EntidadeComConstrutor mane) {
		this.nome = nome;
		this.pedro = mane;
	}
	
	public EntidadeComConstrutor(long numero, String nome) {
		this.nome = nome;
		this.numero=numero;
	}


	public EntidadeComConstrutor(EntidadeComConstrutor mane, String nome) {
		this.nome = nome;
		this.pedro = mane;
	}

	public Object obterPedro() {
		return pedro;
	}

	public String obterNome() {
		return nome;
	}

	public Object obterNumero() {
		return numero;
	}
}
