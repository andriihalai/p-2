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
            if (N < 4) {
                Data.consoleSemaphore.acquire();
                System.out.print(this.getName() + " Enter vector Z: ");
                Data.readZ(this.N);
                System.out.println(this.getName() + " Enter MX:");
                Data.MX = Data.readMatrix(this.N, this.N, this.getName());
            } else {
                Data.Z = Data.getRandomArray(N, -1000, 1000);
                Data.MX = Data.getRandomMatrix(N, -1000, 1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Data.consoleSemaphore.release();
        }

        // Сигнал про закінчення введення даних
        try {
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int[] subZ = Data.getSubArr(Data.Z, Data.threadCount, this.threadId);
        if (subZ.length > 0) {
            int minZ = Data.findMin(subZ);
            // КД1
            Data.setMinZ(minZ);
        }

        // Сигнал про закінчення обчислення zi
        try {
            Data.CL2.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int d4;

        try {
            // КД2
            Data.S2.acquire();
            d4 = Data.d;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Data.S2.release();
        }

        // КД3
        int z4 = Data.z.get();

        // КД4
        int p4 = Data.p.get();

        // Обчислення MAh
        Data.calculateRows(Data.threadCount, this.threadId, d4, z4, p4);

        try {
            // Повідомлення про закінчення обчислення MAh
            Data.CL3.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread " + this.getName() + " finished");
    }
}
