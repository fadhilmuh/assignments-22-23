package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;
    private final String[] jenisPaket = new String[]{"Express","Fast","Reguler"};

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel, Feel free to make any changes
        setLayout(new GridBagLayout());
        initGUI();
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        paketLabel = new JLabel("Paket laundry:");
        paketComboBox = new JComboBox<>(jenisPaket);

        beratLabel = new JLabel("Berat Cucian (kg)");
        beratTextField = new JTextField(10);

        showPaketButton = new JButton("Show Paket");
        showPaketButton.addActionListener(e -> showPaket());

        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000/kg)");
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000/4kg pertama, kemudian 500/kg)");

        createNotaButton = new JButton("Buat Nota");
        backButton = new JButton("Kembali");
        createNotaButton.addActionListener(e -> createNota());
        backButton.addActionListener(e -> handleBack());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 5);

        add(paketLabel,gbc);
        gbc.gridx++;
        add(paketComboBox,gbc);
        gbc.gridx++;
        add(showPaketButton,gbc);
        gbc.gridy++;
        gbc.gridx--;
        add(beratTextField,gbc);
        gbc.gridx = 0;
        add(beratLabel,gbc);
        gbc.gridy++;
        add(setrikaCheckBox,gbc);
        gbc.gridy++;
        add(antarCheckBox,gbc);
        gbc.gridy++;
        gbc.gridwidth = 3;
        add(createNotaButton,gbc);
        gbc.gridy++;
        add(backButton,gbc);
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        String beratText = beratTextField.getText();
        if (!beratText.matches("^[0-9]+")){
            JOptionPane.showMessageDialog(null, "Berat harus berupa angka!", "Info", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int berat = Integer.parseInt(beratText);
        if (berat < 2){
            JOptionPane.showMessageDialog(null, "Cucian kurang dari 2kg akan dianggap sebagai 2kg", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        berat = Math.max(Integer.parseInt(beratText),2);
        Nota nota = new Nota(memberSystemGUI.getLoggedInMember(),berat,(String) paketComboBox.getSelectedItem(), fmt.format(cal.getTime()));
        nota.addService(new CuciService());
        if(setrikaCheckBox.isSelected()){
            nota.addService(new SetrikaService());
        }
        if(antarCheckBox.isSelected()){
            nota.addService(new AntarService());
        }

        NotaManager.addNota(nota);
        memberSystemGUI.getLoggedInMember().addNota(nota);

        if (berat < 2){
            JOptionPane.showMessageDialog(null, "Cucian kurang dari 2kg akan dianggap sebagai 2kg", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, "Nota berhasil dibuat!","Success", JOptionPane.INFORMATION_MESSAGE);
        resetContent();
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        resetContent();
        MainFrame.getInstance().navigateTo(memberSystemGUI.getPageName());
    }

    /**
     * Mereset semua input pada input field
     */
    private void resetContent(){
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText(null);
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }
}
