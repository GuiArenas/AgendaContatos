package controller;

import model.Contato;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AgendaController {

    private static final String ARQUIVO = "agenda.txt";
    private List<Contato> contatos;

    public AgendaController() {
        contatos = carregarAgenda();
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void adicionarContato(Contato contato) {
        contatos.add(contato);
        salvarAgenda();
    }

    public void removerContato(Contato contato) {
        contatos.remove(contato);
        salvarAgenda();
    }

    public List<Contato> buscarContatos(String nome) {
        List<Contato> encontrados = new ArrayList<>();
        for (Contato c : contatos) {
            if (c.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(c);
            }
        }
        return encontrados;
    }

    public void salvarAgenda() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Contato c : contatos) {
                bw.write(c.getNome() + ";" + c.getTelefone() + ";" + c.getEmail());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar a agenda: " + e.getMessage());
        }
    }

    private List<Contato> carregarAgenda() {
        List<Contato> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    lista.add(new Contato(partes[0], partes[1], partes[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar a agenda: " + e.getMessage());
        }
        return lista;
    }

    public void ordenarPorNome() {
        contatos.sort(Comparator.comparing(c -> c.getNome().toLowerCase()));
    }
}
