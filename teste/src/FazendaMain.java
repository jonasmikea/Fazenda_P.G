import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Classe Animal
class Animal {
    private int id;
    private String nome;
    private int idade;
    private int producaoDiaria;
    private boolean produzLeite;

    public Animal(int id, String nome, int idade, boolean produzLeite) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.producaoDiaria = 0;
        this.produzLeite = produzLeite;
    }

    public void registrarProducao(int quantidade) {
        this.producaoDiaria = quantidade;
    }

    public int getProducaoDiaria() {
        return producaoDiaria;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public boolean isProduzLeite() {
        return produzLeite;
    }
}

// Classe Tanque
class Tanque {
    private int id;
    private int capacidadeMaxima;
    private int quantidadeAtual;
    private Date validadeDoLeite;

    public Tanque(int id, int capacidadeMaxima, Date validadeDoLeite) {
        this.id = id;
        this.capacidadeMaxima = capacidadeMaxima;
        this.quantidadeAtual = 0;
        this.validadeDoLeite = validadeDoLeite;
    }

    public boolean armazenarLeite(int quantidade) {
        if (quantidadeAtual + quantidade <= capacidadeMaxima) {
            quantidadeAtual += quantidade;
            return true;
        } else {
            System.out.println("Tanque " + id + " atingiu a capacidade máxima.");
            return false;
        }
    }

    public void removerLeite(int quantidade) {
        if (quantidadeAtual >= quantidade) {
            quantidadeAtual -= quantidade;
        } else {
            System.out.println("Quantidade insuficiente de leite para remover.");
        }
    }

    public int getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public boolean validarQuantidade(int quantidade) {
        return quantidadeAtual + quantidade <= capacidadeMaxima;
    }

    public int getId() {
        return id;
    }

    public Date getValidadeDoLeite() {
        return validadeDoLeite;
    }
}

// Classe Fazenda
class Fazenda {
    private String nome;
    private String endereco;
    private ArrayList<Animal> listaDeAnimais;
    private ArrayList<Tanque> listaDeTanques;

    public Fazenda(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.listaDeAnimais = new ArrayList<>();
        this.listaDeTanques = new ArrayList<>();
    }

    public boolean registrarAnimal(Animal animal) {
        if (buscarAnimalPorId(animal.getId()) != null) {
            System.out.println("Erro: ID do animal já está em uso.");
            return false;
        }
        listaDeAnimais.add(animal);
        System.out.println("Animal " + animal.getNome() + " registrado com sucesso.");
        return true;
    }

    public boolean registrarTanque(Tanque tanque) {
        if (buscarTanquePorId(tanque.getId()) != null) {
            System.out.println("Erro: ID do tanque já está em uso.");
            return false;
        }
        listaDeTanques.add(tanque);
        System.out.println("Tanque ID " + tanque.getId() + " registrado com sucesso.");
        return true;
    }

    public void gerarRelatorioProducao() {
        System.out.println("=== Relatório de Produção ===");
        int producaoTotal = 0;
        for (Animal animal : listaDeAnimais) {
            if (animal.isProduzLeite()) {
                producaoTotal += animal.getProducaoDiaria();
                System.out.println("Animal: " + animal.getNome() + " | Produção Diária: " + animal.getProducaoDiaria() + " litros");
            }
        }
        System.out.println("Produção Total: " + producaoTotal + " litros");
    }

    public void gerarRelatorioArmazenamento() {
        System.out.println("=== Relatório de Armazenamento ===");
        for (Tanque tanque : listaDeTanques) {
            System.out.println("Tanque ID: " + tanque.getId() + " | Quantidade Atual: " + tanque.getQuantidadeAtual() + " litros | Validade: " + new SimpleDateFormat("dd-MM-yyyy").format(tanque.getValidadeDoLeite()));
        }
    }

    public Animal buscarAnimalPorId(int id) {
        for (Animal animal : listaDeAnimais) {
            if (animal.getId() == id) {
                return animal;
            }
        }
        return null;
    }

    public Tanque buscarTanquePorId(int id) {
        for (Tanque tanque : listaDeTanques) {
            if (tanque.getId() == id) {
                return tanque;
            }
        }
        return null;
    }

    public ArrayList<Tanque> getListaDeTanques() {
        return listaDeTanques;
    }
}

// Classe principal com validação de ID, produção de leite e verificação de validade da data
public class FazendaMain {
    public static void main(String[] args) {
        Fazenda fazenda = new Fazenda("Fazenda para a gravação", "jacuma, 40028922");
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("=== Sistema da Fazenda ===");
            System.out.println("1. Registrar Animal");
            System.out.println("2. Registrar Tanque");
            System.out.println("3. Registrar Produção Diária");
            System.out.println("4. Armazenar Leite no Tanque");
            System.out.println("5. Remover Leite do Tanque");
            System.out.println("6. Gerar Relatório de Produção");
            System.out.println("7. Gerar Relatório de Armazenamento");
            System.out.println("0. Sair");

            opcao = inputInt(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    int idAnimal = inputInt(scanner, "ID do Animal: ");
                    scanner.nextLine();
                    System.out.print("Nome do Animal: ");
                    String nomeAnimal = scanner.nextLine();
                    int idade = inputInt(scanner, "Idade do Animal: ");
                    boolean produzLeite = inputBoolean(scanner, "Produz Leite? (s/n): ");
                    Animal animal = new Animal(idAnimal, nomeAnimal, idade, produzLeite);
                    fazenda.registrarAnimal(animal);
                    break;

                case 2:
                    int idTanque = inputInt(scanner, "ID do Tanque: ");
                    int capacidade = inputInt(scanner, "Capacidade Máxima do Tanque: ");
                    Date validade = inputDate(scanner, "Validade do Leite (dd-MM-yyyy): ");
                    Tanque tanque = new Tanque(idTanque, capacidade, validade);
                    fazenda.registrarTanque(tanque);
                    break;

                case 3:
                    if (fazenda.getListaDeTanques().isEmpty()) {
                        int quantidadePretendida = inputInt(scanner, "Produção diária (litros): ");
                        System.out.println("Você não registrou nenhum tanque, recomendo que cadastre um tanque com capacidade superior a " + quantidadePretendida + " litros.");
                    } else {
                        int idProdAnimal = inputInt(scanner, "ID do Animal: ");
                        Animal prodAnimal = fazenda.buscarAnimalPorId(idProdAnimal);
                        if (prodAnimal != null) {
                            if (prodAnimal.isProduzLeite()) { // Verifica se o animal pode produzir leite
                                int producao = inputInt(scanner, "Produção diária (litros): ");
                                prodAnimal.registrarProducao(producao);
                                System.out.println("Produção registrada.");
                            } else {
                                System.out.println("O animal selecionado não produz leite.");
                            }
                        } else {
                            System.out.println("Animal não encontrado.");
                        }
                    }
                    break;

                case 4:
                    if (fazenda.getListaDeTanques().isEmpty()) {
                        int quantidadePretendida = inputInt(scanner, "Quantidade de leite a armazenar (litros): ");
                        System.out.println("Você não registrou nenhum tanque, recomendo que cadastre um tanque com capacidade superior a " + quantidadePretendida + " litros.");
                    } else {
                        int idArmazenar = inputInt(scanner, "ID do Tanque: ");
                        Tanque armazenarTanque = fazenda.buscarTanquePorId(idArmazenar);
                        if (armazenarTanque != null) {
                            int quantidade = inputInt(scanner, "Quantidade de leite a armazenar (litros): ");
                            if (armazenarTanque.armazenarLeite(quantidade)) {
                                System.out.println("Leite armazenado.");
                            }
                        } else {
                            System.out.println("Tanque não encontrado.");
                        }
                    }
                    break;

                case 5:
                    if (fazenda.getListaDeTanques().isEmpty()) {
                        System.out.println("Você não registrou nenhum tanque. Por favor, registre um tanque antes de tentar remover leite.");
                    } else {
                        int idRemover = inputInt(scanner, "ID do Tanque: ");
                        Tanque removerTanque = fazenda.buscarTanquePorId(idRemover);
                        if (removerTanque != null) {
                            int quantidade = inputInt(scanner, "Quantidade de leite a remover (litros): ");
                            removerTanque.removerLeite(quantidade);
                            System.out.println("Leite removido.");
                        } else {
                            System.out.println("Tanque não encontrado.");
                        }
                    }
                    break;

                case 6:
                    if (fazenda.getListaDeTanques().isEmpty()) {
                        System.out.println("Nenhum tanque foi criado. Por favor, registre um tanque antes de gerar o relatório de produção.");
                    } else {
                        fazenda.gerarRelatorioProducao();
                    }
                    break;

                case 7:
                    if (fazenda.getListaDeTanques().isEmpty()) {
                        System.out.println("Nenhum tanque foi criado. Por favor, registre um tanque antes de gerar o relatório de armazenamento.");
                    } else {
                        fazenda.gerarRelatorioArmazenamento();
                    }
                    break;

                case 0:
                    System.out.println("Encerrando sistema.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
        scanner.close();
    }

    public static int inputInt(Scanner scanner, String message) {
        int value;
        while (true) {
            System.out.print(message);
            try {
                value = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número válido.");
                scanner.next();
            }
        }
        return value;
    }

    public static Date inputDate(Scanner scanner, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        Date date = null;

        while (true) {
            System.out.print(message);
            try {
                String input = scanner.next();
                date = dateFormat.parse(input);
                Date today = new Date();
                if (date.before(today)) {
                    System.out.println("A validade deve ser a partir de hoje. Por favor, insira uma data válida.");
                } else {
                    break;
                }
            } catch (ParseException e) {
                System.out.println("Formato inválido. Use o formato dd-MM-yyyy.");
            }
        }
        return date;
    }

    public static boolean inputBoolean(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.next().trim().toLowerCase();
            if (input.equals("s")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Entrada inválida. Por favor, responda com 's' para sim ou 'n' para não.");
            }
        }
    }
}
