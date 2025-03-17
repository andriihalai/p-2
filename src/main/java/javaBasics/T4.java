package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T4 extends MainThread implements Runnable {
    public T4(String name, int N) {
        super(name, N);
    }

    @Override
    public void run() {
        Data.consoleSemaphore.P(this);
        System.out.print(this.getName() + " Enter vector Z: ");
        Data.readZ(this.N);
        Data.consoleSemaphore.V();
        try {
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int start = (int) Math.floor(this.N / 4) * 3;
        int length = (int) (double) (this.N / 4);
        length += this.N % 4;
        int end = start + length;
        int[] subZ = Arrays.copyOfRange(Data.Z, start, end);
        int z4 = Data.findMin(subZ);

        Data.setMinZ(z4);

        System.out.println("Thread " + this.getName() + " finished");
    }
}
