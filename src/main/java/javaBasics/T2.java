package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T2 extends MainThread implements Runnable {
    public T2(String name, int N, int threadId) {
        super(name, N, threadId);
    }

    @Override
    public void run() {
        Data.consoleSemaphore.P(this);
        System.out.println(this.getName() + " Enter MC:");
        Data.MC = Data.readMatrix(this.N, this.N, this.getName());
        System.out.println(this.getName() + " Enter MD:");
        Data.MD = Data.readMatrix(this.N, this.N, this.getName());
        Data.consoleSemaphore.V();

        try {
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int[] subZ = Data.getSubArr(Data.Z, Data.threadCount, this.threadId);
        int minZ = Data.findMin(subZ);
        Data.setMinZ(minZ);

        int d2 = Data.d.get();
        int z2 = Data.z.get();
        int p2 = Data.p.get();

        Data.calculateRows(Data.threadCount, this.threadId, d2, z2, p2);

        System.out.println("Thread " + this.getName() + " finished");
    }
}
