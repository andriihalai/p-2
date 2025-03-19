package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T4 extends MainThread implements Runnable {
    public T4(String name, int N, int threadId) {
        super(name, N, threadId);
    }

    @Override
    public void run() {
        Data.consoleSemaphore.P(this);
        System.out.print(this.getName() + " Enter vector Z: ");
        Data.readZ(this.N);
        System.out.println(this.getName() + " Enter MX:");
        Data.MX = Data.readMatrix(this.N, this.N, this.getName());
        Data.consoleSemaphore.V();
        try {
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int[] subZ = Data.getSubArr(Data.Z, Data.threadCount, this.threadId);
        int minZ = Data.findMin(subZ);

        Data.setMinZ(minZ);

        int d4 = Data.d.get();
        int z4 = Data.z.get();
        int p4 = Data.p.get();

        Data.calculateRows(Data.threadCount, this.threadId, d4, z4, p4);

        System.out.println("Thread " + this.getName() + " finished");
    }
}
