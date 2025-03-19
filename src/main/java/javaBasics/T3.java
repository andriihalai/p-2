package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T3 extends MainThread implements Runnable {
    public T3(String name, int N, int threadId) {
        super(name, N, threadId);
    }

    @Override
    public void run() {
        try {
            if (N < 4) {
                Data.consoleSemaphore.acquire();
                System.out.print(this.getName() + " Enter p: ");
                Data.readP();
            } else {
                Data.getRandomP();
            }
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

        int d3 = Data.d.get();
        int z3 = Data.z.get();
        int p3 = Data.p.get();

        Data.calculateRows(Data.threadCount, this.threadId, d3, z3, p3);

        try {
            Data.CL3.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread " + this.getName() + " finished");
    }
}
