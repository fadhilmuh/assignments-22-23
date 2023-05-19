package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // TODO
        JPanel contentsContainer = new JPanel(new GridBagLayout());
        JPanel buttonsContainer = new JPanel(new GridBagLayout());

        idLabel = new JLabel("Masukkan ID Anda:");
        idTextField = new JTextField(20);

        passwordLabel = new JLabel("Masukkan password Anda:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        backButton = new JButton("Kembali");
        loginButton.addActionListener(e -> handleLogin());
        backButton.addActionListener(e -> handleBack());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        contentsContainer.add(idLabel,gbc);
        gbc.gridy++;
        contentsContainer.add(idTextField,gbc);
        gbc.gridy++;
        contentsContainer.add(passwordLabel,gbc);
        gbc.gridy++;
        contentsContainer.add(passwordField,gbc);

        gbc.gridy = 0;
        buttonsContainer.add(loginButton,gbc);
        gbc.gridy++;
        buttonsContainer.add(backButton,gbc);

        gbc.gridy = 0;
        mainPanel.add(contentsContainer,gbc);
        gbc.gridy++;
        mainPanel.add(buttonsContainer,gbc);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        idTextField.setText(null);
        passwordField.setText(null);
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        String id = idTextField.getText(),
                password = new String(passwordField.getPassword());

        if (MainFrame.getInstance().login(id,password)){
            idTextField.setText(null);
            passwordField.setText(null);
        } else {
            JOptionPane.showMessageDialog(null, "ID atau password invalid!", "Info", JOptionPane.WARNING_MESSAGE);
        }
    }
}
