package assignments.assignment3.nota;
import assignments.assignment1.NotaGenerator;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;

import java.security.Provider;
import java.util.Arrays;

public class Nota {
    private Member member;
    private String paket;
    private LaundryService[] services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int berat;
    private int id;
    private int kompensasi;
    private String tanggalMasuk;
    private boolean isDone;
    static public int totalNota;

    public Nota(Member member, int berat, String paket, String tanggal) {
        this.id = totalNota;
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        this.baseHarga = calculateHarga();
        switch (this.paket.toLowerCase()){
            case "express" -> this.sisaHariPengerjaan = 1;
            case "fast" -> this.sisaHariPengerjaan = 2;
            default -> this.sisaHariPengerjaan = 3;
        }
        totalNota++;
    }

    /**
     * Method untuk menambahkan jenis service ke serviceList
     * @param service object LaundryService yang akan ditambahkan
     */
    public void addService(LaundryService service){
        try {
            services = Arrays.copyOfRange(services, 0, services.length + 1);
            services[services.length - 1] = service;
        } catch (Exception e) {
            services = new LaundryService[1];
            services[0] = service;
        }
        this.baseHarga = calculateHarga();
    }

    /**
     * Method untuk mengerjakan laundry-laundry yang ada
     *
     * @return id Nota dan statusnya
     */
    public String kerjakan(){
        for (LaundryService service: services){
            if (service.isDone())
                continue;
            return String.format("Nota %d : %s\n",this.id,service.doWork());
        }
        return String.format("Nota %d : Sudah Selesai.\n",this.id);
    }

    /**
     * Method untuk menghandle next day
     */
    public void toNextDay() {
        this.sisaHariPengerjaan = (this.sisaHariPengerjaan == -1)?
                this.sisaHariPengerjaan:
                this.sisaHariPengerjaan - 1;

        this.isDone = this.sisaHariPengerjaan <= 0;
        for (LaundryService service: this.services){
            this.isDone = this.isDone && service.isDone();
        }

        if (!(this.isDone) && (this.sisaHariPengerjaan == -1)){
            this.kompensasi++;
            this.baseHarga = (this.baseHarga <= 2000)? 0: this.baseHarga - 2000;
        }
    }

    /**
     * Method yang mengkalkulasikan harga akhir (total)
     *
     * @return (long) harga laundry terbaru
     */
    public long calculateHarga(){
        long price;
        switch (paket.toLowerCase()){
            case "express" -> price = 12000;
            case "fast" -> price = 10000;
            default -> price = 7000;
        }
        price *= berat;
        if (services != null){
            for (LaundryService service : services) {
                price += (service.getHarga(berat));
            }
        }
        return price;
    }

    /**
     * Method untuk mengambil status nota
     *
     * @return  String id nota dan statusnya
     */
    public String getNotaStatus(){
        boolean status = true;
        for (LaundryService service: services){
            status &= service.isDone();
        }
        return String.format("Nota %d : ",this.id) + ((status)? "Sudah selesai.":"Belum selesai.");
    }

    /**
     * Method override untuk mencetak nota
     *
     * @return  String nota dan detilnya
     */
    @Override
    public String toString(){
        String notaStr = NotaGenerator.generateNota(member.getId(), paket, berat, tanggalMasuk);
        notaStr += "\n--- SERVICE LIST ---";
        for (LaundryService service: services){
            notaStr += ("\n-"+service.getServiceName()+" @ Rp."+service.getHarga(this.berat));
        }

        notaStr += "\nHarga akhir: "+ this.baseHarga;
        if (this.sisaHariPengerjaan < 0 && this.kompensasi > 0){
            notaStr += String.format(" Ada kompensasi keterlambatan %d * 2000 hari",this.kompensasi);
        }
        return String.format("\n[ID Nota = %d]\n",this.id) + notaStr;
    }

    // Dibawah ini adalah getter
    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {return isDone;}

    public LaundryService[] getServices(){
        return services;
    }
}
