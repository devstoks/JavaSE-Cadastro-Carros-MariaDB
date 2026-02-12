package service;

import java.util.List;

import dao.CarroDao;
import entidade.Carro;
import service.CarroService;

public class CarroService {

	/**
	 * Classe responsável apenas por: Tratar dados e fazer validações
	 */

	private CarroDao dao = new CarroDao(); // Objeto DAO para conexão com banco de Dados

	// Responsável pelo Cadastro do Carro
	public boolean cadastrar(Carro carro) { // Passa a Entidade Carro como argumento

		try {
			// Recebe os dados da entidade pelo GET e faz uma validação
			if (!validarAno(carro.getAno()) || !validarPlaca(carro.getPlaca()) || !validarNome(carro.getNome())
					|| !validarNome(carro.getCor()) || !validarNome(carro.getMarca())) {

				System.out.println("não foi cadastrado maneiro n");
				return false;
			}

			System.out.println("Foi cadastrado sim");
			// passou nas validações, salva no banco
			if (dao.insertCarro(carro)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	// Método responsável por verificar se a placa passada já foi cadastrada
	public boolean isCadastrada(String placaProcurada) {

		if (dao.placaExiste(placaProcurada)) {
			return true;
		} else {
			return false;

		}

	}

	public Carro localizar(String placa, Carro carro) {
		if (dao.localizar(placa, carro)) {
			return carro;
		} else {
			return null;
		}

	}
	
	public boolean editar(Carro carro) {
		if (dao.editCar(carro)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deletar(String placa) {
		if (dao.deleteByPlate(placa)) {
			return true;
		} else {
			return false;
		}
	}

	
	public boolean criarTabela() {
		if (dao.createTable()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean excluirTabela() {
		if (dao.deleteTable()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean criarBd() {
		if (dao.createDatabase()) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<Carro> listarTodos() {
		return dao.listAllCars();
	}
	// ======= métodos de validação =======

	// Valida uma String de forma genérica
	public boolean validarString(String valor, int tamanhoMinimo) {

		// 1️⃣ null
		if (valor == null) {
			return false;
		}

		// 2️⃣ remove espaços extras
		valor = valor.trim();

		// 3️⃣ vazio
		if (valor.isEmpty()) {
			return false;
		}

		// 4️⃣ tamanho mínimo
		if (valor.length() < tamanhoMinimo) {
			return false;
		}

		return true; // string válida
	}

	// Método para validar se é menor ou igual a 0
	public boolean isInvalid(int valor) {
		return valor <= 0;
	}

	// Remove tudo que NÃO for letra ou número
	// Remove espaços extras e espaços duplos no meio
	public String limparStringAlfanumerica(String valor) {

		if (valor == null) {
			return "";
		}

		valor = valor.trim();

		String resultado = "";
		boolean ultimoFoiEspaco = false;

		for (int i = 0; i < valor.length(); i++) {

			char c = valor.charAt(i);

			// letra ou número
			if (Character.isLetterOrDigit(c)) {
				resultado += c;
				ultimoFoiEspaco = false;
			}
			// espaço (controla espaço duplo)
			else if (c == ' ') {
				if (!ultimoFoiEspaco) {
					resultado += c;
					ultimoFoiEspaco = true;
				}
			}
		}

		return resultado.trim().toUpperCase();
	}

// ============================================================================================== //

	public boolean validarAno(int ano) {

		int anoAtual = 2026;
		int minAno = 1985;

		// 1️⃣ ano inválido (zero ou negativo)
		if (isInvalid(ano)) {
			return false;
		}

		// 2️⃣ verifica se tem exatamente 4 dígitos
		if (String.valueOf(ano).length() != 4) {
			return false;
		}

		// 3️⃣ verifica intervalo permitido
		if (ano < minAno || ano > anoAtual) {
			return false;
		}

		return true; // ano válido
	}

	// Valida nome, marca e cor
	public boolean validarNome(String nome) {

		// reaproveita validação genérica
		if (!validarString(nome, 3)) {
			return false;
		}

		// valida caracteres (somente letras e espaço)
		for (int i = 0; i < nome.length(); i++) {

			char c = nome.charAt(i);

			if (c == ' ') {
				continue;
			}

			if (!Character.isLetter(c)) {
				return false;
			}
		}

		return true;
	}

	public boolean validarPlaca(String placa) {

		if (placa == null || placa.isEmpty()) {
			return false;
		}

		placa = placa.trim().toUpperCase();

		if (placa.length() != 7) {
			return false;
		}

		// MODELO ANTIGO: AAA1234
		boolean modeloAntigo = Character.isLetter(placa.charAt(0)) && Character.isLetter(placa.charAt(1))
				&& Character.isLetter(placa.charAt(2)) && Character.isDigit(placa.charAt(3))
				&& Character.isDigit(placa.charAt(4)) && Character.isDigit(placa.charAt(5))
				&& Character.isDigit(placa.charAt(6));

		// MODELO MERCOSUL: ABC1D23
		boolean modeloMercosul = Character.isLetter(placa.charAt(0)) && Character.isLetter(placa.charAt(1))
				&& Character.isLetter(placa.charAt(2)) && Character.isDigit(placa.charAt(3))
				&& Character.isLetter(placa.charAt(4)) && Character.isDigit(placa.charAt(5))
				&& Character.isDigit(placa.charAt(6));

		return modeloAntigo || modeloMercosul;
	}

}
