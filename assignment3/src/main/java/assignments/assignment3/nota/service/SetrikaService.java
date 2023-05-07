package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    private int methodCallCount;
    @Override
    public String doWork() {
        this.methodCallCount++;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        return (this.methodCallCount == 1);
    }

    @Override
    public long getHarga(int berat) {
        return (long) berat * 1000;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
