package view;

import controller.AgendaController;
import model.Contato;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AgendaGUI extends JFrame {

    private AgendaController controller;

    private JTextField nomeField;
    private JTextField telefoneField;
    private JTextField emailField;
    private JTable contatosTable;
    private DefaultTableModel tableModel;

    public AgendaGUI(AgendaController controller) {
        this.controller = controller;
        setTitle("Agenda de Contatos");
        setSize(800, 600);
        setMinimumSize(new Dimension(700, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Degradê de fundo
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 250, 240);
                Color color2 = new Color(220, 245, 255);
                g2d.setPaint(new GradientPaint(0, 0, color1, 0, getHeight(), color2));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        });

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);

        // Campos de entrada
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        nomeField = new JTextField();
        nomeField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        telefoneField = new JTextField();
        telefoneField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputPanel.add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputPanel.add(emailField, gbc);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Botões com Material Design
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        buttonPanel.setOpaque(false);

        JButton addButton = createMaterialButton("Adicionar", new Color(76, 175, 80));
        JButton listarButton = createMaterialButton("Listar", new Color(33, 150, 243));
        JButton buscarButton = createMaterialButton("Buscar", new Color(255, 193, 7));
        JButton editarButton = createMaterialButton("Editar", new Color(255, 87, 34));
        JButton excluirButton = createMaterialButton("Excluir", new Color(244, 67, 54));

        buttonPanel.add(addButton);
        buttonPanel.add(listarButton);
        buttonPanel.add(buscarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(excluirButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Tabela responsiva
        String[] colunas = {"Nome", "Telefone", "Email"};
        tableModel = new DefaultTableModel(colunas, 0);
        contatosTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false; // tabela não editável diretamente
            }
        };
        contatosTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contatosTable.setRowHeight(28);
        contatosTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        contatosTable.setSelectionBackground(new Color(100, 149, 237));
        contatosTable.setSelectionForeground(Color.WHITE);
        contatosTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(contatosTable);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Listeners
        addButton.addActionListener(e -> adicionarContato());
        listarButton.addActionListener(e -> listarContatos());
        buscarButton.addActionListener(e -> buscarContatos());
        editarButton.addActionListener(e -> editarContato());
        excluirButton.addActionListener(e -> excluirContato());
    }

    private JButton createMaterialButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover e efeito clique
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(color);
            }
        });

        // Borda arredondada
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(button.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);
                g2.dispose();
                super.paint(g, c);
            }
        });

        return button;
    }

    private void adicionarContato() {
        String nome = nomeField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();

        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        controller.adicionarContato(new Contato(nome, telefone, email));
        JOptionPane.showMessageDialog(this, "Contato adicionado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limparCampos();
        listarContatos();
    }

    private void listarContatos() {
        controller.ordenarPorNome();
        atualizarTabela(controller.getContatos());
    }

    private void buscarContatos() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome para buscar:");
        if (nome != null && !nome.isEmpty()) {
            List<Contato> encontrados = controller.buscarContatos(nome);
            atualizarTabela(encontrados);
        }
    }

    private void editarContato() {
        int row = contatosTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um contato na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Contato c = controller.getContatos().get(row);

        String novoNome = JOptionPane.showInputDialog(this, "Nome:", c.getNome());
        String novoTel = JOptionPane.showInputDialog(this, "Telefone:", c.getTelefone());
        String novoEmail = JOptionPane.showInputDialog(this, "Email:", c.getEmail());

        c.setNome(novoNome);
        c.setTelefone(novoTel);
        c.setEmail(novoEmail);
        controller.ordenarPorNome();
        listarContatos();
    }

    private void excluirContato() {
        int row = contatosTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um contato na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Contato c = controller.getContatos().get(row);
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir " + c.getNome() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.removerContato(c);
            listarContatos();
        }
    }

    private void atualizarTabela(List<Contato> contatos) {
        tableModel.setRowCount(0);
        for (Contato c : contatos) {
            tableModel.addRow(new Object[]{c.getNome(), c.getTelefone(), c.getEmail()});
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        telefoneField.setText("");
        emailField.setText("");
    }
}
