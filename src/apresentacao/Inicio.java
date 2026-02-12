package apresentacao;

import java.util.Scanner;

import entidade.Carro;
import service.CarroService;

public class Inicio {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		String op;
		do {
			// Menu
			System.out.println("\n -- Menu -- ");
			System.out.println(" Escolha uma Opção: ");
			System.out.println(" 1- Para Cadastrar  ");
			System.out.println(" 2 - Para Localizar ");
			System.out.println(" 3- Para Editar ");
			System.out.println(" 4 - Para Excluir ");
			System.out.println("-- Modo ADMIN --");
			System.out.println(" 5 - Para criar BD e Tabela ");
			System.out.println(" 6 - Para Excluir Tabela ");
			System.out.println(" 8 - Para Listar Todos");
			System.out.println(" S - Para Sair ");
			System.out.print("Escolha uma opção: ");

			op = scanner.nextLine();
			switch (op) {
			case "1": { // Para Cadastrar
				CarroService service = new CarroService();
				Carro carroNovo = new Carro();

				System.out.println("\n Você escolheu 1 - Para cadastrar um carro");

				// MARCA
				System.out.print("Digite a Marca: ");
				String marca = scanner.nextLine();
				while (!service.validarNome(marca)) {
					System.out.println("Marca inválida. Tente novamente.");
					System.out.print("Digite a Marca: ");
					marca = scanner.nextLine();
				}
				carroNovo.setMarca(marca);

				// NOME
				System.out.print("Digite o Nome: ");
				String nome = scanner.nextLine();
				while (!service.validarNome(nome)) {
					System.out.println("Nome inválido. Tente novamente.");
					System.out.print("Digite o Nome: ");
					nome = scanner.nextLine();
				}
				carroNovo.setNome(nome);

				// PLACA
				System.out.print("Digite a Placa: ");
				String placa = scanner.nextLine();

				while (service.isCadastrada(placa)) {
					System.out.println("Placa já cadastrada! Tente novamente.");
					System.out.print("Digite a Placa: ");
					placa = scanner.nextLine();
				}

				while (!service.validarPlaca(placa)) {
					System.out.println("Placa inválida. Tente novamente.");
					System.out.print("Digite a Placa: ");
					placa = scanner.nextLine();
				}
				carroNovo.setPlaca(placa);

				// COR
				System.out.print("Digite a Cor: ");
				String cor = scanner.nextLine();
				while (!service.validarNome(cor)) {
					System.out.println("Cor inválida. Tente novamente.");
					System.out.print("Digite a Cor: ");
					cor = scanner.nextLine();
				}
				carroNovo.setCor(cor);

				// ANO
				System.out.print("Digite o Ano: ");
				int ano = scannerInteiroSeguro(scanner);
				while (!service.validarAno(ano)) {
					System.out.println("Ano inválido. Tente novamente.");
					System.out.print("Digite o Ano: ");
					ano = scannerInteiroSeguro(scanner);
				}
				carroNovo.setAno(ano);

				// Chama o método de cadastro no Service

				if (service.cadastrar(carroNovo)) {
					System.out.println("\nCarro cadastrado com sucesso!");

				} else {
					System.out.println("Erro ao cadastrar o carro. Verifique os dados e tente novamente.");
				}

				break;
			}

			case "2": {// Para Localizar

				// Insira a placa, ou modelo e ano
				CarroService service = new CarroService(); // Objeto para validação
				Carro carro = new Carro();

				System.out.println("\n Você escolheu 2 - Para Localizar Carro");

				System.out.print("Digite a Placa do veículo: ");
				String placa = scanner.nextLine();

				// repete enquanto a marca for inválida
				while (!service.validarPlaca(placa)) {
					System.out.println("Placa inválida. Tente novamente.");
					System.out.print("Digite a Placa: ");
					placa = scanner.nextLine();
				}

				// busca a placa e retorna o objeto

				Carro carroLocalizado = service.localizar(placa, carro);

				if (carroLocalizado != null) {
					System.out.println("\nCarro localizado!");
					System.out.println("Marca: " + carroLocalizado.getMarca());
					System.out.println("Nome: " + carroLocalizado.getNome());
					System.out.println("Cor: " + carroLocalizado.getCor());
					System.out.println("Placa: " + carroLocalizado.getPlaca());
					System.out.println("Ano: " + carroLocalizado.getAno());
				} else {
					System.out.println("\nNão foi possível localizar o carro pela placa inserida!");
				}

				break;

			}

			case "3": { // Para Editar
				CarroService service = new CarroService(); // Objeto para validação
				Carro carro = new Carro();

				System.out.println("\n Você escolheu 3 - Para Editar Carro");
				System.out.print("Digite a Placa do veículo que deseja editar: ");
				String placa = scanner.nextLine();

				// Valida a placa antes de prosseguir
				while (!service.validarPlaca(placa)) {
					System.out.println("Placa inválida. Tente novamente.");
					System.out.print("Digite a Placa: ");
					placa = scanner.nextLine();
				}

				// busca a placa e retorna o objeto
				Carro carroLocalizado = service.localizar(placa, carro);

				// caso o carro tenha sido encontrado
				if (carroLocalizado != null) {
					System.out.println("Carro encontrado!"); // exibe mensagem de encontrado

					// Solicita os novos valores, mantendo os antigos como padrão
					// Nova Marca
					System.out.print("Digite a nova Marca (ou pressione Enter para manter a atual: ");
					String novaMarca = scanner.nextLine();

					// Caso a informação nova seja "vazia"
					if (novaMarca.isEmpty()) {
						novaMarca = carro.getMarca(); // pegar a informação já registrada
					}

					// Loop para manter a informação correta antes de seguir
					while (!service.validarNome(novaMarca)) {
						System.out.println("Marca inválida. Tente novamente.");
						System.out.print("Digite a nova Marca (ou pressione Enter para manter a atual: ");
						novaMarca = scanner.nextLine();
					}

					// Novo Nome
					System.out.print("Digite o novo Nome (ou pressione Enter para manter o atual: ");
					String novoNome = scanner.nextLine();

					// Caso a informação nova seja "vazia"
					if (novoNome.isEmpty()) {
						novoNome = carro.getNome(); // pegar a informação já registrada
					}

					// Loop para manter a informação correta antes de seguir
					while (!service.validarNome(novoNome)) {
						System.out.println("Nome inválido. Tente novamente.");
						System.out.print("Digite o novo nome (ou pressione Enter para manter a atual: ");
						novoNome = scanner.nextLine();
					}

					// Nova cor
					System.out.print("Digite a nova Cor (ou pressione Enter para manter a atual: ");
					String novaCor = scanner.nextLine();

					// Caso a informação nova seja "vazia"
					if (novaCor.isEmpty()) {
						novaCor = carro.getCor(); // pegar a informação já registrada
					}

					// Loop para manter a informação correta antes de seguir
					while (!service.validarNome(novaCor)) {
						System.out.println("cor inválida. Tente novamente.");
						System.out.print("Digite a nova cor (ou pressione Enter para manter a atual: ");
						novaCor = scanner.nextLine();
					}

					// Novo Ano
					System.out.print("Digite o novo Ano (ou pressione Enter para manter o atual: ");
					String anoInput = scanner.nextLine();
					int novoAno = carro.getAno();

					if (!anoInput.isEmpty()) {
						try {
							novoAno = Integer.parseInt(anoInput);
						} catch (NumberFormatException e) {
							System.out.println("Ano inválido. Mantendo o ano atual."); // pegar a informação já
																						// registrada
						}
					}

					// Loop para manter a informação correta antes de seguir
					while (!service.validarAno(novoAno)) {
						System.out.println("Ano inválido. Tente novamente.");
						System.out.print("Digite o Ano: ");
						novoAno = scannerInteiroSeguro(scanner);
					}

					// Atualizando os dados no objeto carro
					carroLocalizado.setMarca(novaMarca);
					carroLocalizado.setNome(novoNome);
					carroLocalizado.setCor(novaCor);
					carroLocalizado.setAno(novoAno);

					// Chamando o método de edição no banco de dados
					boolean sucesso = service.editar(carroLocalizado);

					if (sucesso) {
						System.out.println("Carro atualizado com sucesso!");
					} else {
						System.out.println("Falha ao atualizar o carro.");
					}

				} else {
					System.out.println("Carro não encontrado.");
				}

				break;
			}

			case "4": { // Para Excluir
				CarroService service = new CarroService(); // Objeto para validação
				Carro carro = new Carro();

				System.out.println("\n Você escolheu 4 - Para Excluir Carro");
				System.out.print("Digite a Placa do veículo: ");
				String placa = scanner.nextLine();

				// Validação da placa
				while (!service.validarPlaca(placa)) {
					System.out.println("Placa inválida. Tente novamente.");
					System.out.print("Digite a Placa do veículo: ");
					placa = scanner.nextLine();
				}

				// busca a placa e retorna o objeto
				Carro carroLocalizado = service.localizar(placa, carro);

				if (carroLocalizado != null) {

					// Mostra os dados antes de excluir
					System.out.println("\nCarro encontrado:");
					System.out.println("Marca: " + carroLocalizado.getMarca());
					System.out.println("Nome: " + carroLocalizado.getNome());
					System.out.println("Cor: " + carroLocalizado.getCor());
					System.out.println("Placa: " + carroLocalizado.getPlaca());
					System.out.println("Ano: " + carroLocalizado.getAno());

					// Confirmação
					System.out.print("\nTem certeza que deseja excluir este carro? (S/N): ");
					String confirmacao = scanner.nextLine();

					if (confirmacao.equalsIgnoreCase("S")) {

						boolean sucesso = service.deletar(placa);

						if (sucesso) {
							System.out.println("Carro excluído com sucesso!");
						} else {
							System.out.println("Erro ao excluir o carro.");
						}

					} else {
						System.out.println("Exclusão cancelada.");
					}

				} else {
					System.out.println("Carro não encontrado.");
				}

				break;
			}

			// ==================== ADMIN ==========================

			case "5": {// Para Criar Tabela

				CarroService service = new CarroService(); // Objeto para validação

				System.out.println("\n Você selecionou 5 - Criar tabela");

				if (service.criarTabela()) {
					System.out.println("Tabela Criada com sucesso!");
				} else {
					System.out.println("Erro! Não foi possivel criar tabela");
				}
				break;
			}

			case "6": {// Para Excluir BD e Tabela

				CarroService service = new CarroService(); // Objeto para validação

				System.out.println("\n Você selecionou 6 - Excluir tabela");

				System.out.print("\nTem certeza que deseja excluir a tabela (S/N): ");

				String confirmacao = scanner.nextLine();

				// Confirmação antes de deletar
				if (confirmacao.equalsIgnoreCase("S")) {

					boolean sucesso = service.excluirTabela();

					if (sucesso) {
						System.out.println("Tabela deletada com sucesso!");
					} else {
						System.out.println("Não foi possível deletar tabela!");
					}

				} else {
					System.out.println("Exclusão cancelada.");
				}

				break;
			}

			case "7": { // Criando Banco de Dados
				CarroService service = new CarroService(); // Objeto para validação
				System.out.println("\n Você selecionou 7 - Criar Banco de Dados");

				if (service.criarBd()) {
					System.out.println("Banco de dados Criado com sucesso!");
				} else {
					System.out.println("Não foi possível criar banco de dados!");
				}

				break;
			}
			
			
			case "8": { // Listar todos os Carros cadastrados
				CarroService service = new CarroService(); // Objeto para validação
				System.out.println("\n Você escolheu 8 - Listar todos os carros");

				// Busca todos os carros do banco
				var listaCarros = service.listarTodos();

				if (listaCarros.isEmpty()) {
					System.out.println("Nenhum carro cadastrado.");
				} else {
					System.out.println("\n--- Lista de Carros ---");
					for (Carro c : listaCarros) {
						System.out.println("----------------------");
						System.out.println("Marca: " + c.getMarca());
						System.out.println("Nome: " + c.getNome());
						System.out.println("Cor: " + c.getCor());
						System.out.println("Placa: " + c.getPlaca());
						System.out.println("Ano: " + c.getAno());
					}
				}

				break;
			}
			case "s": {// Para Sair

				break;
			}
			default:
				break;
			}

		} while (!op.equals("s"));

	}

	public static int scannerInteiroSeguro(Scanner scanner) {
		while (true) {
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.print("Digite apenas números: ");
			}
		}
	}

}