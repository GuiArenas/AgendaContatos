import controller.AgendaController;
import model.Contato;
import view.AgendaGUI;
import view.Menu;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AgendaController controller = new AgendaController();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha a interface:");
        System.out.println("1 - GUI");
        System.out.println("2 - Console");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) {
            SwingUtilities.invokeLater(() -> new AgendaGUI(controller));
        } else {
            Menu menu = new Menu();
            while (true) {
                System.out.println("\n===== AGENDA =====");
                menu.mostrarMenu();
                int opcao = menu.lerOpcao();
                scanner.nextLine();
                switch (opcao) {
                    case 1:
                        String nome = menu.lerNome();
                        String telefone = menu.lerTelefone();
                        String email = menu.lerEmail();
                        controller.adicionarContato(new Contato(nome, telefone, email));
                        System.out.println("Contato adicionado!");
                        break;
                    case 2:
                        controller.ordenarPorNome();
                        controller.getContatos().forEach(c -> {
                            System.out.println("-------------------");
                            System.out.println(c);
                        });
                        break;
                    case 3:
                        String busca = menu.lerNome();
                        controller.buscarContatos(busca).forEach(c -> {
                            System.out.println("-------------------");
                            System.out.println(c);
                        });
                        break;
                    case 4:
                        String excluir = menu.lerNome();
                        var possiveis = controller.buscarContatos(excluir);
                        if (possiveis.isEmpty()) {
                            System.out.println("Nenhum contato encontrado para exclusão.");
                        } else {
                            var contatoExcluir = possiveis.get(0);
                            System.out.print("Confirmar exclusão de " + contatoExcluir.getNome() + "? (S/N): ");
                            String conf = scanner.nextLine();
                            if (conf.equalsIgnoreCase("S")) {
                                controller.removerContato(contatoExcluir);
                                System.out.println("Contato removido!");
                            } else {
                                System.out.println("Exclusão cancelada.");
                            }
                        }
                        break;
                    case 5:
                        String editar = menu.lerNome();
                        var encontradosEditar = controller.buscarContatos(editar);
                        if (!encontradosEditar.isEmpty()) {
                            var c = encontradosEditar.get(0);
                            String novoNome = menu.lerLinha("Novo nome (" + c.getNome() + "): ");
                            if (!novoNome.isEmpty()) c.setNome(novoNome);
                            String novoTel = menu.lerLinha("Novo telefone (" + c.getTelefone() + "): ");
                            if (!novoTel.isEmpty()) c.setTelefone(novoTel);
                            String novoEmail = menu.lerLinha("Novo email (" + c.getEmail() + "): ");
                            if (!novoEmail.isEmpty()) c.setEmail(novoEmail);
                            controller.ordenarPorNome();
                            System.out.println("Contato atualizado!");
                        } else {
                            System.out.println("Nenhum contato encontrado.");
                        }
                        break;
                    case 6:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        }
    }
}
