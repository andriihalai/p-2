package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T4 extends MainThread implements Runnable {
    public T4(String name, int N, int threadId) {
        super(name, N, threadId);
    }

    @Override
    public void run() {
        // Введення даних
        try {
            if (N < 4) {
                Data.consoleSemaphore.acquire();
                System.out.print(this.getName() + " Enter vector Z: ");
                Data.readZ(this.N);
                System.out.println(this.getName() + " Enter MD:");
                Data.MD = Data.readMatrix(this.N, this.N, this.getName());
            } else {
                Data.Z = Data.getRandomArray(N, -1000, 1000);
                Data.MD = Data.getRandomMatrix(N, -1000, 1000);
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
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        // КД 2
        int d4 = Data.d.get();

        int z4;

        try {
            // КД 3
            Data.S2.acquire();
            z4 = Data.z;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Data.S2.release();
        }

        // КД4
        int p4 = Data.p.get();

        // Обчислення MAh
        Data.calculateRows(Data.threadCount, this.threadId, d4, z4, p4);

        try {
            // Повідомлення про закінчення обчислення MAh
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread " + this.getName() + " finished");
    }
}
