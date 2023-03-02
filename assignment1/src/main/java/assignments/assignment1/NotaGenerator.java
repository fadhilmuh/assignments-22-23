//Fadhil Muhammad
//2206083464
//TP 01
//DDP2 E

// Import modules dan package
package assignments.assignment1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class NotaGenerator { // main class
    public static void main(String[] args) throws IOException{
        assignments.assignment1.Reader.init(System.in); // untuk menerima input
        printMenu();
        String choice = assignments.assignment1.Reader.nextString(), name, id, phoneNumber,paket; // declare variabel dan meminta input

        switch (choice){ // lanjutkan program sesuai input
            case "0":
                System.out.print("================================\n" +
                        "Terima kasih telah menggunakan NotaGenerator!\n");
                System.exit(0);
                break;
            case "1":
                System.out.println("Masukkan nama Anda: ");
                name = assignments.assignment1.Reader.nextString();
                System.out.println("Masukkan nomor handphone Anda: ");
                phoneNumber = assignments.assignment1.Reader.nextString();
                while(!phoneNumber.matches("[0-9]+")){
                    System.out.println("Nomor hp hanya menerima digit");
                    phoneNumber = assignments.assignment1.Reader.nextString();
                }
                name = name.split("\\s+")[0].toUpperCase();
                id = generateId(name,phoneNumber);
                System.out.println("ID Anda : "+id);
                break;
            case "2":
                System.out.println("Masukkan nama Anda: "); // input nama
                name = assignments.assignment1.Reader.nextString();
                System.out.println("Masukkan nomor handphone Anda: ");
                phoneNumber = assignments.assignment1.Reader.nextString(); // input nomor telepon
                while(!phoneNumber.matches("[0-9]+")){
                    System.out.println("Nomor hp hanya menerima digit");
                    phoneNumber = assignments.assignment1.Reader.nextString();
                }
                name = name.split("\\s+")[0].toUpperCase();
                id = generateId(name,phoneNumber);

                System.out.println("Masukkan tanggal terima:");
                String tanggalTerima = assignments.assignment1.Reader.nextString(); // input tanggal

                System.out.println("Masukkan paket laundry:");
                paket = assignments.assignment1.Reader.nextString().toLowerCase(); // input paket laundry
                String[] types = {"express","fast","reguler"};
                while(!Arrays.asList(types).contains(paket)){
                    if(!paket.equals("?")) {
                        System.out.println("Paket " + paket + " tidak diketahui\n" +
                                "[ketik ? untuk mencari tahu jenis paket]\n"+
                                "Masukkan paket laundry:");
                        paket = assignments.assignment1.Reader.nextString().toLowerCase();
                    }
                    while (paket.equals("?")){
                        showPaket();
                        System.out.println("Masukkan paket laundry:");
                        paket = assignments.assignment1.Reader.nextString().toLowerCase();
                    }
                }

                System.out.println("Masukkan berat cucian Anda [Kg]:");
                int berat = 0;
                while(true) {
                    try {
                        berat = assignments.assignment1.Reader.nextInt();
                        if(berat <= 0)
                            throw new IOException();
                        break;
                    } catch (Exception e){
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                    }
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.println(generateNota(id, paket, berat, tanggalTerima));
                break;
            default:
                System.out.print("================================\n" +
                        "Perintah tidak diketahui, silakan periksa kembali\n");
                break;
        }
        main(args); // ulangi program sampai memilih [0]
    }

    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
        System.out.print("Pilihan : ");
    }

    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    static public int checkSum(String token,int index){ // fungsi rekursif untuk check sum karakter string
        return (token.length() == index)? 0:
                (token.substring(index,index+1).matches("^[A-Z]$"))? token.charAt(index) - 'A' + 1  + checkSum(token,index+1) :
                        (token.substring(index,index+1).matches("^[0-9]$"))? token.charAt(index) - '0' + checkSum(token,index+1):
                                7 + checkSum(token,index+1);
    }

    public static String generateId(String name, String phoneNumber){ // fungsi untuk men-generate id
        name = name.split("\\s+")[0].toUpperCase();
        String token = name+"-"+phoneNumber;
        String newToken = Integer.toString(checkSum(token,0));
        return (newToken.length() >= 2)? token+"-"+newToken.substring(newToken.length()-2): token+"-"+"0"+newToken;
    }

    public static String generateNota(String id, String paket, int berat, String tanggalTerima){ // fungsi untuk men-generate nota
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(tanggalTerima, formatter);
        LocalDate endDate;
        int price;

        switch (paket){
            case "express":
                endDate = date.plusDays(1);
                price = 12000;
                break;
            case "fast":
                endDate = date.plusDays(2);
                price = 10000;
                break;
            default:
                endDate = date.plusDays(3);
                price = 7000;
                break;
        }
        berat = Math.max(2,berat);
        System.out.println("Nota Laundry");
        String out =
                "ID    : "+id +"\n"+
                "Paket : "+paket + "\n"+
                "Harga :\n" +
                berat +" kg x "+price+" = "+berat*price+"\n" +
                "Tanggal Terima  : "+date.format(formatter)+"\n" +
                "Tanggal Selesai : "+endDate.format(formatter);

        System.out.print((berat == 2)? "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg\n":"");
        return out;
    }
}

class Reader{ // custom class untuk membaca input dari terminal
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    static void init(InputStream input){
        reader = new BufferedReader(new InputStreamReader(input));
        tokenizer = new StringTokenizer("");
    }
    static String next() throws IOException {
        while (!tokenizer.hasMoreTokens()){
            tokenizer = new StringTokenizer(reader.readLine());
        } return tokenizer.nextToken();
    }
    static int nextInt() throws IOException{
        return Integer.parseInt(next());
    }
    static String nextString() throws IOException {
        return reader.readLine();
    }
}