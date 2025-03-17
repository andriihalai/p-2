package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T2 extends MainThread implements Runnable {
    public T2(String name, int N) {
        super(name, N);
    }

    @Override
    public void run() {
//        Data.consoleSemaphore.P(this);
//        System.out.println(this.getName() + " Enter MC:");
//        Data.MC = Data.readMatrix(this.N, this.N, this.getName());
//        System.out.println(this.getName() + " Enter MD:");
//        Data.MD = Data.readMatrix(this.N, this.N, this.getName());
//        Data.consoleSemaphore.V();

        try {
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int start = (int) (double) (this.N / 4);
        int length = (int) (double) (this.N / 4);
        int end = start + length;
        int[] subZ = Arrays.copyOfRange(Data.Z, start, end);
        int z2 = Data.findMin(subZ);

        Data.setMinZ(z2);

        System.out.println("Thread " + this.getName() + " finished");
    }
}
