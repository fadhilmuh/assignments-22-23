package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {
    private int idNota, berat, sisaHariPengerjaan;
    private String paket, tanggalMasuk;
    private Member member;
    private boolean isReady;

    public Nota(Member member, String paket, int berat, String tanggalMasuk, int idNota, int sisaHariPengerjaan) {
        // TODO: buat constructor untuk class ini
        this.idNota = idNota;
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan = sisaHariPengerjaan;
        this.isReady = this.sisaHariPengerjaan <= 0;
    }

    public void updateSisaHariPengerjaan(){
        this.sisaHariPengerjaan--;
        this.isReady = this.sisaHariPengerjaan <= 0;
    }

    /**
     * Method untuk mengambil id nota
     *
     * @return nomor id nota
     */
    public int getIdNota() {
        return this.idNota;
    }

    /**
     * Method untuk mengambil total sisa hari
     *
     * @return sisa hari pengerjaan
     */
    public int getSisaHariPengerjaan() {
        return this.sisaHariPengerjaan;
    }

    /**
     * Method untuk mengambil status cucian
     *
     * @return boolean status kesiapan cucian
     */
    public boolean isReady() {
        return isReady;
    }
}
