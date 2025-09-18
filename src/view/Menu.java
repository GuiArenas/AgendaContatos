package view;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("[1] Adicionar contato");
        System.out.println("[2] Listar contatos");
        System.out.println("[3] Buscar contatos");
        System.out.println("[4] Excluir contato");
        System.out.println("[5] Editar contato");
        System.out.println("[6] Sair");
        System.out.print("Escolha uma opção: ");
    }

    public int lerOpcao() {
        return scanner.nextInt();
    }

    public String lerNome() {
        System.out.print("Digite o nome: ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    public String lerTelefone() {
        System.out.print("Digite o telefone: ");
        return scanner.nextLine();
    }

    public String lerEmail() {
        System.out.print("Digite o email: ");
        return scanner.nextLine();
    }

    public String lerLinha(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
}