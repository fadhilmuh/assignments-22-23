package assignments.assignment3.user.menu;
import assignments.assignment1.NotaGenerator;
import assignments.assignment3.MainMenu;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import com.sun.tools.javac.Main;

import java.util.Arrays;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice){
            case 1 -> laundry();
            case 2 -> detailNota();
            case 3 -> logout = true;
            default -> System.out.println("Pilihan tidak valid.");
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        memberList = Arrays.copyOfRange(memberList,0,memberList.length + 1);
        memberList[memberList.length - 1] = member;
    }

    /**
     * Method untuk menjalankan opsi buat laundry baru
     */
    public void laundry(){
        System.out.println("Masukan paket laundry:");
        NotaGenerator.showPaket();
        String paket = in.next().toLowerCase();
        String[] availablePaket = new String[]{"express","fast","reguler"};
        while(!Arrays.asList(availablePaket).contains(paket)){
            if(!paket.equals("?")) {
                System.out.println("Paket " + paket + " tidak diketahui\n" +
                        "[ketik ? untuk mencari tahu jenis paket]\n"+
                        "Masukkan paket laundry:");
                paket = in.next().toLowerCase();
            }
            while (paket.equals("?")){
                NotaGenerator.showPaket();
                System.out.println("Masukkan paket laundry:");
                paket = in.next().toLowerCase();
            }
        }

        System.out.println("Masukkan berat cucian Anda [Kg]:");
        int berat = 0;
        while (berat <= 0){
            try {
                berat = in.nextInt();
                if (berat <= 0){
                    throw new Exception();
                }
                in.nextLine();
            } catch (Exception e) {
                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
            }
        }
        if (berat < 2)
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        berat = Math.max(2,berat);

        System.out.print(
                "Apakah kamu ingin cucianmu disetrika oleh staff professional kami?\n" +
                "Hanya tambah 1000 / kg :0\n" +
                "[Ketik x untuk tidak mau]: ");
        String choiceInput = in.nextLine();

        boolean setrika = (!choiceInput.equalsIgnoreCase("x"));
        System.out.print(
                "Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!\n" +
                "Cuma 2000 / 4kg, kemudian 500 / kg\n" +
                "[Ketik x untuk tidak mau]: ");
        choiceInput = in.nextLine();
        boolean antar = (!choiceInput.equalsIgnoreCase("x"));

        createNota(paket,berat,setrika,antar);
    }

    /**
     * Method untuk membuat objek Nota baru
     *
     * @param paket     Jenis paket
     * @param berat     Jumlah berat
     * @param setrika   Service setrika (yes/no)
     * @param antar     Service antar (yes/no)
     */
    private void createNota(String paket,int berat, boolean setrika, boolean antar){
        Nota nota = new Nota(loginMember, berat, paket, NotaManager.fmt.format(NotaManager.cal.getTime()));
        LaundryService cuci = new CuciService();
        nota.addService(cuci);
        if (setrika){
            LaundryService setrikaService = new SetrikaService();
            nota.addService(setrikaService);
        }
        if (antar){
            LaundryService antarService = new AntarService();
            nota.addService(antarService);
        }
        NotaManager.addNota(nota);
        loginMember.addNota(nota);
        System.out.println("Nota berhasil dibuat!");
    }

    /**
     * Mencetak semua detail nota-nota yang ada
     */
    public void detailNota(){
        try {
            for (Nota nota : loginMember.getNotaList()) {
                System.out.println(nota);
            }
        } catch (Exception e){
            System.out.print("");
        }
    }
}