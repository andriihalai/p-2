package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T3 extends MainThread implements Runnable {
    public T3(String name, int N, int threadId) {
        super(name, N, threadId);
    }

    @Override
    public void run() {
        // Введення даних
        try {
            if (N < 4) {
                Data.consoleSemaphore.acquire();
                System.out.print(this.getName() + " Enter p: ");
                Data.readP();
                System.out.println(this.getName() + " Enter MC:");
                Data.MC = Data.readMatrix(this.N, this.N, this.getName());
            } else {
                Data.getRandomP();
                Data.MC = Data.getRandomMatrix(N, -1000, 1000);
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
        int d3 = Data.d.get();

        int z3;

        try {
            // КД 3
            Data.S2.acquire();
            z3 = Data.z;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Data.S2.release();
        }

        // КД4
        int p3 = Data.p.get();

        // Обчислення MAh
        Data.calculateRows(Data.threadCount, this.threadId, d3, z3, p3);

        try {
            // Повідомлення про закінчення обчислення MAh
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread " + this.getName() + " finished");
    }
}
