package entidade;

public class Carro {

	/**
	 * Classe Responsável pelos Atributos, GET e SET e Contrutor da entidade CARRO
	 * no sistema
	 */

	private String marca;
	private String nome;
	private String cor;
	private String placa;
	private int ano;

	// Construtor vazio
	public Carro() {
		super();
	}

	// Construtor cheio
	public Carro(String marca, String nome, String cor, String placa, int ano) {
		super();
		this.marca = marca;
		this.nome = nome;
		this.cor = cor;
		this.placa = placa;
		this.ano = ano;
	}

	// Metodos Get & Set
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
	
}
