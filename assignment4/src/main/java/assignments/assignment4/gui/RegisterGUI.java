package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
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
        nameLabel = new JLabel("Masukkan nama Anda:");
        phoneLabel = new JLabel("Masukkan nomor handphone Anda:");
        passwordLabel = new JLabel("Masukkan password Anda");

        int width = 20;
        nameTextField = new JTextField(width);
        phoneTextField = new JTextField(width);
        passwordField = new JPasswordField(width);

        JPanel buttonsContainer = new JPanel(new GridBagLayout());
        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> handleRegister());
        backButton = new JButton("Kembali");
        backButton.addActionListener(e -> handleBack());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        mainPanel.add(nameLabel,gbc);
        gbc.gridy++;
        mainPanel.add(nameTextField,gbc);
        gbc.gridy += 2;
        mainPanel.add(phoneLabel,gbc);
        gbc.gridy++;
        mainPanel.add(phoneTextField,gbc);
        gbc.gridy += 2;
        mainPanel.add(passwordLabel,gbc);
        gbc.gridy++;
        mainPanel.add(passwordField,gbc);
        gbc.gridy++;
        mainPanel.add(buttonsContainer,gbc);

        gbc.gridy = 0;
        buttonsContainer.add(registerButton,gbc);
        gbc.gridy++;
        buttonsContainer.add(backButton,gbc);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        String nama = nameTextField.getText(),
                noHp = phoneTextField.getText(),
                password = new String(passwordField.getPassword());

        if (nama.isBlank()){
            JOptionPane.showMessageDialog(null, "Semua field di atas tidak boleh kosong!", "Info", JOptionPane.WARNING_MESSAGE);
            nameTextField.setText(null);
            return;
        } if (noHp.isBlank()){
            JOptionPane.showMessageDialog(null, "Semua field di atas tidak boleh kosong!", "Info", JOptionPane.WARNING_MESSAGE);
            phoneTextField.setText(null);
            return;
        } if (password.isEmpty()){
            JOptionPane.showMessageDialog(null, "Semua field di atas tidak boleh kosong!", "Info", JOptionPane.WARNING_MESSAGE);
            return;
        }  if (!noHp.matches("^[0-9]+")){
            JOptionPane.showMessageDialog(null, "Nomor HP harus berupa angka!", "Info", JOptionPane.WARNING_MESSAGE);
            phoneTextField.setText(null);
            return;
        }

        Member newMember = loginManager.register(nama, noHp, password);
        if (newMember == null){
            JOptionPane.showMessageDialog(null, String.format("User dengan nama %s dan nomor HP %s sudah ada!", nama, noHp), "Info", JOptionPane.ERROR_MESSAGE);
            MainFrame.getInstance().navigateTo(HomeGUI.KEY);
            resetField();
            return;
        }

        JOptionPane.showMessageDialog(null, String.format("Berhasil membuat user dengan ID %s", newMember.getId()), "Info", JOptionPane.INFORMATION_MESSAGE);
        resetField();
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    private void resetField(){
        nameTextField.setText(null);
        phoneTextField.setText(null);
        passwordField.setText(null);
    }
}
