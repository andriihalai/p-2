package javaBasics;

import java.util.concurrent.BrokenBarrierException;

public class T1 extends MainThread implements Runnable {
    public T1(String name, int N, int threadId) {
        super(name, N, threadId);
    }

    @Override
    public void run() {

        Data.consoleSemaphore.P(this);
        System.out.print(this.getName() + " Enter d: ");
        Data.readD();
        Data.consoleSemaphore.V();

        try {
            Data.CL1.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        int[] subZ = Data.getSubArr(Data.Z, Data.threadCount, this.threadId);
        int minZ = Data.findMin(subZ);
        Data.setMinZ(minZ);

        int d1 = Data.d.get();
        int z1 = Data.z.get();
        int p1 = Data.p.get();

        Data.calculateRows(Data.threadCount, this.threadId, d1, z1, p1);

        System.out.println("Thread " + this.getName() + " finished");
    }
}
