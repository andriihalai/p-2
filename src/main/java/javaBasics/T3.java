package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T3 extends MainThread implements Runnable {
    public T3(String name, int N) {
        super(name, N);
    }

    @Override
    public void run() {
//        Data.consoleSemaphore.P(this);
//        System.out.print(this.getName() + " Enter p: ");
//        Data.readP();
//        Data.consoleSemaphore.V();

        try {
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int start = (int) Math.floor(this.N / 4) * 2;
        int length = (int) (double) (this.N / 4);
        int end = start + length;
        int[] subZ = Arrays.copyOfRange(Data.Z, start, end);
        int z3 = Data.findMin(subZ);

        Data.setMinZ(z3);

        System.out.println("Thread " + this.getName() + " finished");
    }
}
