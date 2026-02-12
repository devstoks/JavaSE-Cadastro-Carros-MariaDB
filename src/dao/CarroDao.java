package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidade.Carro;

/**
 * Classe responsável apenas por acessar o banco de dados (SQL, conexão, insert,
 * update, delete)
 */

// CRIAR BANCO DE DADOS

public class CarroDao { // Este Método apenas estabelece a conexão e cria um Banco de Dados, caso não
							// criado ainda

	private String URL = "jdbc:mariadb://localhost:3306/BDaula01_02"; // url de conexão já passando o banco de dados
	private String USER = "root"; // define o nome do usuário no servidor
	private String PASSWORD = ""; // define a senha do usuário no servidor como (vazia)

	// Connection é uma interface do Java que representa uma conexão ativa com o
	// banco de dados.
	// Este método não retorna um tipo primitivo, mas sim um objeto do tipo
	// Connection,
	// permitindo reutilizar e centralizar a lógica de conexão com o banco.
	// A conexão é feita a partir daqui. Não precisa repetir, apenas utiliza o
	// método,
	// Permitindo executar comandos SQL (SELECT, INSERT, UPDATE, DELETE).
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD); // DriverManager, específica do driver do banco
																	// (MariaDB, MySQL, etc.).
	}

	// CRIAR BANCO DE DADOS (está aqui apenas por teste)
	public boolean createDatabase() {
		try {

			// Para executar um comando SQL, eu preciso de uma conexão com o banco de dados.
			// GetConnection() Cria e retorna uma conexão com o banco
			// "Connection conn = ... " guardo a conexão em uma variável.
			// A variável conn passa a representar: O canal aberto entre o Java e o Banco de
			// Dados
			// Impede a repetição de código para abrir a conexão
			Connection conn = getConnection();

			// Variável que recebe o comando para executar no banco de dados
			String sqlCreateDatabase = "CREATE DATABASE IF NOT EXISTS BDaula01_02;"; // cria o BDaula01_02 caso não
																						// exista
			PreparedStatement ps = conn.prepareStatement(sqlCreateDatabase);

			if(ps.executeUpdate() == 1){ // executa a tarefa
				// Fechando conexão
				ps.close();
				conn.close();
				return true; // retornando informação
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// CRIAR TABELAS
	// Este Método apenas cria a tabela com os atributos da Classe
	public boolean createTable() {
		try {
			Connection conn = getConnection();
			// Criar tabela
			String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Carro (id INT AUTO_INCREMENT PRIMARY KEY, marca varchar(30) not null, nome varchar(30) not null, cor varchar(10) not null, placa varchar(7) unique not null, ano int not null);";
			PreparedStatement ps = conn.prepareStatement(sqlCreateTable);
			ps.execute();

			// Fechando a conexão
			ps.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace(); // mostra erro no log
			return false;
		}
	}

	// INSERIR VALORES NA TABELA (chamado pela entidade Carro)

	// Este Método apenas recebe os valores do objeto já cadastrado e insere nas
	// respectivas tabelas do banco de dados
	public boolean insertCarro(Carro carro) {
		String sql = "INSERT INTO Carro (marca, nome, cor, placa, ano) VALUES (?, ?, ?, ?, ?)";

		// Estabele a conexão
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, carro.getMarca());
			ps.setString(2, carro.getNome());
			ps.setString(3, carro.getCor());
			ps.setString(4, carro.getPlaca());
			ps.setInt(5, carro.getAno());

			if (ps.executeUpdate() > 0) {
				conn.close(); // fechando a conexão
				return true;
			} else {
				conn.close(); // fechando a conexão
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace(); // mostra erro no log
			return false;
		}
	}

	// DELETAR TABELA
	public boolean deleteTable() {
		try {
			Connection conn = getConnection();
			// prepara o comando a ser executado no bd
			String sql = "DROP TABLE IF EXISTS Carro;";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute(); // executa o comando no bd
			conn.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace(); // mostra erro no log
			return false;
		}
	}

	// MÉTODO PARA BUSCAR PELA PLACA DO VEÍCULO
	public boolean localizar(String placaDigitada, Carro carro) {

		try {

			// Estabelecendo a conexão com o Banco de dados
			Connection conn = getConnection();

			// Consulta SQL que busca as colunas (marca,nome,cor,placa e ano) filtrando pelo valor da placa
			// será substituído pelo valor real que você fornecer depois.
			String query = "SELECT marca, nome, cor, placa, ano FROM Carro WHERE placa = ?";

			// Executa o comando
			PreparedStatement ps = conn.prepareStatement(query);

			// Isso garante que o valor da placa será inserido
			ps.setString(1, placaDigitada);

			// recebe o resultado da consulta de uma query

			ResultSet resultado = ps.executeQuery();

			// verifica se há pelo menos uma linha retornada. Se houver, ele posiciona o
			if (resultado.next()) {
				// Cada atributo do objeto Carro recebe o valor correspondente do banco de dados.
				carro.setMarca(resultado.getString("marca"));
				carro.setNome(resultado.getString("nome"));
				carro.setCor(resultado.getString("cor"));
				carro.setPlaca(resultado.getString("placa"));
				carro.setAno(resultado.getInt("ano"));
				
				// Fechando recursos
				resultado.close();
				ps.close();
				conn.close();

				return true;
			}

			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // Caso ocorra alguma exceção, retornamos null
	}

	// MÉTODO PARA EDITAR OS DADOS DO CARRO
	public boolean editCar(Carro carro) {
		try {
			// Estabelecendo a conexão com o banco de dados
			Connection conn = getConnection();

			// Consulta SQL para atualizar os dados do carro
			String sqlUpdate = "UPDATE Carro SET marca = ?, nome = ?, cor = ?, ano = ? WHERE placa = ?";

			// Preparando a declaração SQL
			PreparedStatement ps = conn.prepareStatement(sqlUpdate);

			// Definindo os valores dos parâmetros na ordem correta
			ps.setString(1, carro.getMarca());
			ps.setString(2, carro.getNome());
			ps.setString(3, carro.getCor());
			ps.setInt(4, carro.getAno());
			ps.setString(5, carro.getPlaca());

			// Executando a atualização
			int linhasAfetadas = ps.executeUpdate();

			// Fechando recursos
			ps.close();
			conn.close();

			// Se pelo menos uma linha foi afetada, a atualização foi bem-sucedida
			return linhasAfetadas > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Se ocorrer algum erro, retorna falso
	}

	// MÉTODO PARA EXCLUIR CARRO PELA PLACA
	public boolean deleteByPlate(String placa) {

		try {
			Connection conn = getConnection();

			String sql = "DELETE FROM Carro WHERE placa = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, placa);

			int linhasAfetadas = ps.executeUpdate();

			ps.close();
			conn.close();

			// Se apagou pelo menos 1 linha, deu certo
			return linhasAfetadas > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	// MÉTODO PARA LISTAR TODOS OS CARROS CADASTRADOS
	public List<Carro> listAllCars() {

	    List<Carro> listaCarros = new ArrayList<>();

	    try {
	        // Estabelecendo a conexão com o banco de dados
	        Connection conn = getConnection();

	        // Consulta SQL para buscar todos os carros
	        String sql = "SELECT marca, nome, cor, placa, ano FROM Carro";

	        // Preparando o comando SQL
	        PreparedStatement ps = conn.prepareStatement(sql);

	        // Executando a consulta
	        ResultSet resultado = ps.executeQuery();

	        // Enquanto existir linha no resultado
	        while (resultado.next()) {

	            // Criando um novo objeto Carro para cada registro
	            Carro carro = new Carro();

	            // Preenchendo o objeto com os dados do banco
	            carro.setMarca(resultado.getString("marca"));
	            carro.setNome(resultado.getString("nome"));
	            carro.setCor(resultado.getString("cor"));
	            carro.setPlaca(resultado.getString("placa"));
	            carro.setAno(resultado.getInt("ano"));

	            // Adicionando o carro na lista
	            listaCarros.add(carro);
	        }

	        // Fechando recursos
	        resultado.close();
	        ps.close();
	        conn.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // Retorna a lista (vazia se não houver registros)
	    return listaCarros;
	}
	
	
	public boolean placaExiste(String placaDigitada) {

	    String sql = "SELECT 1 FROM Carro WHERE placa = ?";

	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, placaDigitada);
	        ResultSet rs = ps.executeQuery();

	        return rs.next(); // true se encontrou, false se não

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
}
