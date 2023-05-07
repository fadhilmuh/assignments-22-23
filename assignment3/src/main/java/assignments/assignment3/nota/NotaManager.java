package assignments.assignment3.nota;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class NotaManager {
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    public static Calendar cal = Calendar.getInstance();
    static public Nota[] notaList = new Nota[0];

    /**
     * Skips ke hari berikutnya dan update semua entri nota yang sesuai.
     */
    public static void toNextDay(){
        cal.add(cal.DATE,1);
        for (Nota nota: notaList){
            nota.toNextDay();
        }
    }

    /**
     * Menambahkan nota baru ke NotaList.
     *
     * @param nota Nota object untuk ditambahkan.
     */
    public static void addNota(Nota nota){
        try {
            notaList = Arrays.copyOfRange(notaList, 0, notaList.length + 1);
            notaList[notaList.length - 1] = nota;
        } catch (Exception e){
            notaList = new Nota[1];
            notaList[0] = nota;
        }
    }
}
