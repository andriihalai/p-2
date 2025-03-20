package javaBasics;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T1 extends MainThread implements Runnable {
    public T1(String name, int N, int threadId) {
        super(name, N, threadId);
    }

    @Override
    public void run() {
        // Введення даних
        try {
            if (N < 4) {
                Data.consoleSemaphore.acquire();
                System.out.print(this.getName() + " Enter d: ");
                Data.readD();
            } else {
                Data.getRandomD();
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
        int d1 = Data.d.get();

        int z1;

        try {
            // КД 3
            Data.S2.acquire();
            z1 = Data.z;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Data.S2.release();
        }

        // КД 4
        int p1 = Data.p.get();

        // Обчислення MAh
        Data.calculateRows(Data.threadCount, this.threadId, d1, z1, p1);
        try {
            // Повідомлення про закінчення обчислення MAh
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println("MA: " + Arrays.deepToString(Data.MA));

        System.out.println("Thread " + this.getName() + " finished");
    }
}
