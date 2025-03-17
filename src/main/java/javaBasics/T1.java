package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T1 extends MainThread implements Runnable {
    public T1(String name, int N) {
        super(name, N);
    }

    @Override
    public void run() {

        Data.consoleSemaphore.P(this);
        System.out.print(this.getName() + " Enter d: ");
        Data.readD();
        Data.consoleSemaphore.V();

        try {
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int start = 0;
        int length = (int) (double) (this.N / 4);
        int end = start + length;
        int[] subZ = Arrays.copyOfRange(Data.Z, start, end);
        int z1 = Data.findMin(subZ);

        Data.setMinZ(z1);

        System.out.println("Thread " + this.getName() + " finished");
    }
}
