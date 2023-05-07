/**
* Tugas Pemrograman 2
* Dasar-Dasar Pemrograman 2
*
* @author Fadhil Muhammad - 2206083464
*/

package assignments.assignment2;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static LocalDate currentDate;
    private static Nota[] notaList;
    private static Member[] memberList;
    private static String[] types = {"express","fast","reguler"};
    private static int idNota = 0;

    public static void main(String[] args) {
        boolean isRunning = true;
        currentDate = LocalDate.parse(fmt.format(cal.getTime()),formatter);
        while (isRunning) {
            input = new Scanner(System.in);
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
        input.close();
    }

    /**
     *  Method untuk menghandle perintah GeneraateUser
     */
    private static void handleGenerateUser(){
        String nama, noHp, id;

        System.out.println("Masukan nama Anda:");
        nama = input.nextLine();

        System.out.println("Masukan nomor handphone Anda:");
        noHp = getNoHp();

        switch (validateMember(nama,noHp)){
            case 1:
                id = generateId(nama,noHp);
                System.out.printf("Berhasil membuat member dengan ID %s!\n",id);

                Member newMember = new Member(nama,noHp,id);
                updateMemberList(newMember);
                break;

            default:
                System.out.print("");
                break;
        }
    }

    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
        System.out.println("Masukan ID member:");
        String id = input.next(), paket, nota;
        Nota newNota;
        int berat = 0, sisaHariPengerjaan;
        if (idAvailability(id)){
            Member member = getMember(id);
            member.setBonusCounter();

            System.out.println("Masukan paket laundry:");
            input = new Scanner(System.in);
            paket = input.nextLine();

            while(!Arrays.asList(types).contains(paket.toLowerCase())){
                if(!paket.equals("?")) {
                    System.out.println("Paket " + paket + " tidak diketahui\n" +
                            "[ketik ? untuk mencari tahu jenis paket]\n"+
                            "Masukkan paket laundry:");
                    paket = input.nextLine();
                }
                while (paket.equals("?")){
                    showPaket();
                    System.out.println("Masukkan paket laundry:");
                    paket = input.nextLine();
                }
            }
            paket = paket.substring(0,1).toUpperCase() + paket.substring(1);
            System.out.println("Masukkan berat cucian Anda [Kg]:");
            while(true) {
                try {
                    berat = input.nextInt();
                    if(berat <= 0)
                        throw new IOException();
                    break;
                } catch (Exception e){
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                }
            }
            nota = generateNota(id,paket.toLowerCase(),berat,currentDate.format(formatter).toString(),false,(member.getBonusCounter())? 50: 0);


            System.out.println("Berhasil menambahkan nota!");
            System.out.printf("[ID Nota = %d]\n",idNota);
            System.out.println(nota);

            sisaHariPengerjaan = (paket.equalsIgnoreCase("Express"))? 1:
                                    (paket.equalsIgnoreCase("fast"))? 2: 3;

            newNota = new Nota(getMember(id),paket,berat,fmt.format(cal.getTime()),idNota,sisaHariPengerjaan);
            updateNotaList(newNota);
            idNota++;
            System.out.println("Status\t: Belum bisa diambil :(");
        } else {
            System.out.printf("Member dengan ID %s tidak ditemukan!\n",id);
        }

    }

    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
        if (notaList != null) {
            String status;
            int jumlahMember = notaList.length,id, sisaHari;
            System.out.printf("Terdaftar %d nota dalam sistem.\n", jumlahMember);

            for (Nota nota : notaList) {
                id = nota.getIdNota();
                sisaHari = nota.getSisaHariPengerjaan();
                status = (sisaHari <= 0)? "Sudah dapat diambil!": "Belum bisa diambil :(";
                System.out.printf("- [%d] Status\t: %s\n",id,status);
            }
        } else {
            System.out.println("Terdaftar 0 nota dalam sistem");
        }
    }

    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
        if (memberList != null) {
            int jumlahMember = memberList.length;
            System.out.printf("Terdaftar %d member dalam sistem.\n", jumlahMember);

            for (Member member : memberList) {
                System.out.printf("- %s : %s\n", member.getId(), member.getNama());
            }
        } else {
            System.out.println("Terdaftar 0 member dalam sistem");
        }
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
        System.out.println("Masukan ID nota yang akan diambil:");
        int idNota = input.nextInt();
        Nota nota = getNota(idNota);
        if (nota != null) {
            boolean isReady = nota.isReady();
            if (isReady) {
                removeNota(idNota);
                System.out.printf("Nota dengan ID %d berhasil diambil!\n",idNota);
            } else {
                System.out.printf("Nota dengan ID %d gagal diambil!\n", idNota);
            }
        } else {
            System.out.printf("Nota dengan ID %d tidak ditemukan!\n",idNota);
        }
    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
        currentDate = LocalDate.parse(currentDate.format(formatter),formatter).plusDays(1);
        System.out.println("Dek Depe tidur hari ini... zzz...");
        if (notaList != null)
            for (Nota nota: notaList) {
                nota.updateSisaHariPengerjaan();
                if (nota.getSisaHariPengerjaan() <= 0)
                    System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n", nota.getIdNota());
            }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n",currentDate.format(formatter)) ;
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    /*
     *  Method untuk meminta input noHp dan memvalidasinya
     */
    private static String getNoHp(){
        String noHp = input.next();

        if (noHp.matches("[0-9]+"))
            return noHp;

        System.out.println("Field nomor hp hanya menerima digit.");
        return getNoHp();
    }

    /*
     *  Method untuk mengupdate memberList
     */
    private static void updateMemberList(Member newMember){
        if(memberList == null) {
            memberList = new Member[1];
            memberList[0] = newMember;
        }
        else{
            memberList = Arrays.copyOf(memberList,memberList.length + 1);
            memberList[memberList.length - 1] = newMember;
        }
    }

    /**
    *  Method untuk mengupdate list kumpulan nota pelanggan
    */
    private static void updateNotaList(Nota newNota){
        if(notaList == null) {
            notaList = new Nota[1];
            notaList[0] = newNota;
        }
        else{
            notaList = Arrays.copyOf(notaList,notaList.length + 1);
            notaList[notaList.length - 1] = newNota;
        }
    }

    /**
     *  Method untuk validasi member baru
     */
    private static int validateMember(String nama, String noHp){
        if (memberList != null)
            for(Member member: memberList){
                if(member.getId().equals(generateId(nama,noHp))){
                    System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n",nama,noHp);
                    return 0;
                }
            }
        return 1;
    }

    /**
     * Method untuk mengecek ketersediaan Id member
     *
     * @return nilai boolean
     */
    private static boolean idAvailability(String id){
        if (!(memberList == null))
            for (Member member: memberList)
                if (id.equals(member.getId()))
                    return true;

        return false;
    }

    /**
     *  Method untuk mencari member dari list member berdasarkan id
     *
     *  @return Object member'
     */
    private static Member getMember(String id){
        for (Member member: memberList)
            if (member.getId().equals(id))
                return member;
        return null;
    }

    /**
     * method untuk menghapus item dari list nota
     */
    public static void removeNota(int idNota) {
        // Create a new array with size one less than the original array
        Nota[] newArr = new Nota[notaList.length - 1];

        // Iterate over the original array and copy all elements except the one to remove
        int index = 0;
        for (int i = 0; i < notaList.length; i++) {
            if (!(notaList[i].getIdNota() == idNota)) {
                newArr[index] = notaList[i];
                index++;
            }
        }

        notaList = newArr;
    }

    /**
     * Method untuk mencari nota dari list nota berdasarkan id
     *
     * @return object dari class Nota
     */
    public static Nota getNota(int idNota){
        if (notaList != null){
            for (Nota nota : notaList)
                if (nota.getIdNota() == idNota)
                    return nota;
        }
        return null;
    }


    /**
     *  Method untuk mencetak paket yang tersedia
     */
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
}
