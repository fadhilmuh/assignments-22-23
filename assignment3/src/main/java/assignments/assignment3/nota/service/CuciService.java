package assignments.assignment3.nota.service;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

public class CuciService implements LaundryService{
    private int methodCallCount;
    @Override
    public String doWork() {
        this.methodCallCount++;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        return this.methodCallCount == 1;
    }

    @Override
    public long getHarga(int berat) {
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
