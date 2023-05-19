package assignments.assignment4.gui;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

import static assignments.assignment3.nota.NotaManager.*;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

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
        JPanel homeLabelPanel = new JPanel(new FlowLayout(1,0,0));
        titleLabel = new JLabel("Selamat datang di CuciCuci System!");
        homeLabelPanel.add(titleLabel);

        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setLayout(new GridBagLayout());
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        toNextDayButton = new JButton("Next Day");

        loginButton.addActionListener(e -> handleToLogin());
        registerButton.addActionListener(e -> handleToRegister());
        toNextDayButton.addActionListener(e -> handleNextDay());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 20, 10);

        homeButtonPanel.add(loginButton,gbc);
        gbc.gridy++;
        homeButtonPanel.add(registerButton,gbc);
        gbc.gridy++;
        homeButtonPanel.add(toNextDayButton,gbc);

        JPanel dateLabelPanel = new JPanel(new FlowLayout());
        dateLabel = new JLabel(String.format("Hari ini: %s",fmt.format(cal.getTime())));
        dateLabelPanel.add(dateLabel,BorderLayout.CENTER);

        gbc.insets = new Insets(0,0,0,0);
        gbc.gridy = 0;
        mainPanel.add(homeLabelPanel,gbc);
        gbc.gridy++;
        mainPanel.add(homeButtonPanel,gbc);
        gbc.gridy++;
        mainPanel.add(dateLabelPanel,gbc);
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame.getInstance().navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        toNextDay();
        dateLabel.setText(String.format("Hari ini: %s",fmt.format(cal.getTime())));
        JOptionPane.showMessageDialog(null, "Kamu tidur hari ini...zzz...","Tidurlah....tidurlah...",JOptionPane.INFORMATION_MESSAGE);
    }
}
