import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Bank;
import entities.User;
import repositories.BankRepository;
import repositories.UserRepository;
import utils.SearchUser;

public class App {
	public static void main(String[] args) throws Exception {
		Locale.setDefault(Locale.US);
		Scanner scanner = new Scanner(System.in);
		UserRepository userRepository = new UserRepository();
		BankRepository bankRepository = new BankRepository();

		String option = "1";

		while(option != "10") {
			System.out.println("Digite 1 para criar um usuário");
			System.out.println("Digite 2 para colocar dinheiro em um usuário");
			System.out.println("Digite 3 para criar um banco");
			System.out.println("Digite 4 para se associar a um banco");
			System.out.println("Digite 5 para depositar no banco");
			System.out.println("Digite 6 para sacar no banco");
			System.out.println("Digite 7 para ver seu cadastro como usuário");
			System.out.println("Digite 8 para listar os usuário");
			System.out.println("Digite 9 para listar todos os bancos");
			System.out.println("Digite 10 para sair");
			option = scanner.nextLine();

			switch (option) {
				case "1": {
					System.out.println("\n --------------------------------------------- \n");
					System.out.println("Digite seu nome:");
					String userName = scanner.nextLine();
					System.out.println("Digite seu email:");
					String userEmail = scanner.nextLine();
					System.out.println("Digite uma senha:");
					String userPassword = scanner.nextLine();
					userRepository.registerUser(userName, userEmail, userPassword);
					System.out.println("\n\nUsuário criado.");
					System.out.println("\n --------------------------------------------- \n");
					break;
				}
				case "2": {
					System.out.println("Digite o email do usuário:");
					String userEmail = scanner.nextLine();
					System.out.println("Digite a senha do usuário:");
					String userPassword = scanner.nextLine();
					
					User user =  SearchUser.getUser(userEmail, userPassword, userRepository);

					if(user == null) {
						System.out.println("Usuário não encontrado");
						break;
					}

					System.out.println("Digite a quantia quer quer colocar:");
					Double userMoney = scanner.nextDouble();
					scanner.nextLine();
					user.increaseMoney(userMoney);
					System.out.println("Foi adicionado: R$" + userMoney + " Ao usuário: " + user);
					System.out.println("\n --------------------------------------------- \n");
					break;
				}
				
				case "3": 
					System.out.println("\n --------------------------------------------- \n");
					System.out.println("Digite o nome de um banco para cria-lo");
					String bankName = scanner.nextLine();
					bankRepository.create(bankName);
					System.out.println("Banco criado.");
					System.out.println("\n --------------------------------------------- \n");
					break;

				case "4": {
					System.out.println("\n --------------------------------------------- \n");
					Boolean confirmBank;
					System.out.println("Digite o nome do banco que você quer se associar");
					String bankNameTarget = scanner.nextLine();

					Bank bankTarget = bankRepository.getBankByName(bankNameTarget);

					if(bankRepository.getBankByName(bankNameTarget) == null) {
						System.out.println("Não existe esse banco!");
						break;
					}

					System.out.println("Digite\n[SIM] para se associar ao banco\n" + bankTarget.getName() + "\n[NAO] para negar:");
					String choice = scanner.nextLine();

					if(choice.toLowerCase().equals("sim")) {
						confirmBank = true;
					} else {
						confirmBank = false;
					}

					if(confirmBank == false) {
						break;
					}

					System.out.println("Vc confirmou!");

					System.out.println("Digite o email do usuário:");
					String userEmail = scanner.nextLine();
					System.out.println("Digite a senha do usuário:");
					String userPassword = scanner.nextLine();

					User userTarget =  SearchUser.getUser(userEmail, userPassword, userRepository);

					if(userTarget == null) {
						System.out.println("Usuário não encontrado");
						break;
					}

					userTarget.associateUserToBank(bankNameTarget, bankRepository);
					bankTarget.addUserRepository(userTarget);
					
					System.out.println("Usuário\n" + userTarget + "\nFoi associado");
					System.out.println("\n --------------------------------------------- \n");
					break;
				}

			case "5": {
				System.out.println("\n --------------------------------------------- \n");
				System.out.println("Digite o email do usuário:");
				String userEmail = scanner.nextLine();
				System.out.println("Digite a senha do usuário:");
				String userPassword = scanner.nextLine();

				User userTarget =  SearchUser.getUser(userEmail, userPassword, userRepository);

				System.out.println("Digite o valor que você quer depositar no banco");
				Double moneyToBank = scanner.nextDouble();
				scanner.nextLine();

				System.out.println("Digite o nome do seu banco:");
				String bankNameDeposit = scanner.nextLine();
				
				Bank bank = bankRepository.getBankByName(bankNameDeposit);

				if(bank == null) {
					System.out.println("Não existe este banco");
				}

				Boolean bankNameIsEqualUserBank = userTarget.getBank().getName().equals(bankNameDeposit);

				if(bankNameIsEqualUserBank == false) {
					System.out.println("Esse não é seu banco");
					break;
				}

				bank.deposit(userTarget, moneyToBank);
				System.out.println("Depositado");
				System.out.println("\n --------------------------------------------- \n");
				break;
			}

			case "6": {
				System.out.println("\n --------------------------------------------- \n");
				System.out.println("Digite o email do usuário:");
				String userEmail = scanner.nextLine();
				System.out.println("Digite a senha do usuário:");
				String userPassword = scanner.nextLine();

				User userTarget =  SearchUser.getUser(userEmail, userPassword, userRepository);

				System.out.println("Digite o valor que você quer sacar no banco");
				Double moneyToWithdrawn = scanner.nextDouble();
				scanner.nextLine();

				System.out.println("Digite o nome do seu banco:");
				String bankNameWithdrawn = scanner.nextLine();
				
				Bank bank = bankRepository.getBankByName(bankNameWithdrawn);

				if(bank == null) {
					System.out.println("Não existe este banco");
				}

				Boolean bankNameIsEqualUserBank = userTarget.getBank().getName().equals(bankNameWithdrawn);

				if(bankNameIsEqualUserBank == false) {
					System.out.println("Esse n ão é seu banco");
					break;
				}

				bank.withdrawn(userTarget, moneyToWithdrawn);
				System.out.println("Sacado");
				System.out.println("\n --------------------------------------------- \n");
				break;
			}

			case "7": {
				System.out.println("\n --------------------------------------------- \n");
				System.out.println("Digite o email do usuário:");
				String userEmail = scanner.nextLine();
				System.out.println("Digite a senha do usuário:");
				String userPassword = scanner.nextLine();

				User user = SearchUser.getUser(userEmail, userPassword, userRepository);

				if(user == null) {
					System.out.println("Usuário não existe.");
					break;
				}

				System.out.println(user);
				System.out.println("\n --------------------------------------------- \n");
				break;
			}

			case "8": {
				System.out.println("\n --------------------------------------------- \n");
				userRepository.listUsers();
				System.out.println("\n --------------------------------------------- \n");
				break;
			}

			case "9": {
				System.out.println("\n --------------------------------------------- \n");
				bankRepository.listAllBanks();
				System.out.println("\n --------------------------------------------- \n");
				break;
			}

			case "10": 
				System.out.println("Saindo");
				default:
					System.out.println("Opção inexistente.");
					break;
			}
		}
		
		scanner.close();
	}
}
