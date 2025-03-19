package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T4 extends MainThread implements Runnable {
    public T4(String name, int N, int threadId) {
        super(name, N, threadId);
    }

    @Override
    public void run() {
        try {
            Data.consoleSemaphore.acquire();
            System.out.print(this.getName() + " Enter vector Z: ");
            Data.readZ(this.N);
            System.out.println(Arrays.toString(Data.Z));
            System.out.println(this.getName() + " Enter MX:");
            Data.MX = Data.readMatrix(this.N, this.N, this.getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Data.consoleSemaphore.release();
        }

        try {
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int[] subZ = Data.getSubArr(Data.Z, Data.threadCount, this.threadId);
        if (subZ.length > 0) {
            int minZ = Data.findMin(subZ);
            Data.setMinZ(minZ);
        }

        try {
            Data.CL2.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int d4 = Data.d.get();
        int z4 = Data.z.get();
        int p4 = Data.p.get();

        Data.calculateRows(Data.threadCount, this.threadId, d4, z4, p4);

        try {
            Data.CL3.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread " + this.getName() + " finished");
    }
}
