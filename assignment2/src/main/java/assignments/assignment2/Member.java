package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String nama, noHp, id;
    private int bonusCounter;

    public Member(String nama, String noHp, String id) {
        this.nama = nama;
        this.noHp = noHp;
        this.id = id;
        this.bonusCounter = 0;
    }

    /**
     * Method setter dan getter untuk mengatur dan mengambil atribut dari class ini
     */
    public String getNama() {
        return nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getId() {
        return id;
    }

    public boolean getBonusCounter() {
        return (bonusCounter % 3 == 0);
    }

    public void setBonusCounter() {
        this.bonusCounter++;
    }
}
