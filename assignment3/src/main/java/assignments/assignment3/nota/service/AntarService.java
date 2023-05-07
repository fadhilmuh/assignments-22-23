package assignments.assignment3.nota.service;

import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    private  int methodCallCount;
    @Override
    public String doWork() {
        this.methodCallCount++;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return this.methodCallCount == 1;
    }

    @Override
    public long getHarga(int berat) {
        return (berat <= 4)? 2000: 2000 + (long) (berat-4)*500;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
