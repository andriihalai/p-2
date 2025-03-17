package javaBasics;

import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Data {
    public static final int threadCount = 4;
    public static int N = 0;
    public static int d;
    public static int p;
    public static int[] Z;
    public static int[][] MC;
    public static int[][] MD;

    public static int z;

    public static Semaphore consoleSemaphore = new Semaphore(1);
    public static final CyclicBarrier CL1 = new CyclicBarrier(Data.threadCount);
    private static final ReentrantLock L1 = new ReentrantLock();

    public static void setN(int N) {
        Data.N = N;
    }

    public static int readScalar() {
        Scanner scanner = new Scanner(System.in);
        int result;

        while (true) {
            String value = scanner.nextLine();
            try {
                result = Integer.parseInt(value);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer input. Please enter a valid integer.");
            }
        }

        return result;
    }

    public static int[] readVector(int n) {
        Scanner scanner = new Scanner(System.in);
        String vector = scanner.nextLine();
        String[] elements = vector.split("\\s+");
        int [] res = new int[n];
        for (int i = 0; i < elements.length; i++) {
            res[i] = Integer.parseInt(elements[i]);
        }
        return res;
    }

    public static int[][] readMatrix(int n, int m, String threadName) {
        int[][] matrix = new int[n][m];
         for (int i = 0; i < n; i++) {
             System.out.print(threadName + " Row " + (i + 1) + ": ");
             int[] row = Data.readVector(n);
             matrix[i] = row;
         }

         return matrix;
    }

    public static int findMin(int[] vector) {
        if (vector.length == 0) {
            System.out.println("Length of vector has to be more that 0");
            return -1;
        }
        int min = vector[0];
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < min) {
                min = vector[i];
            }
        }
        return min;
    }

    public static void setMinZ(int zi) {
        Data.L1.lock();
        Data.z = Math.min(Data.z, zi);
        Data.L1.unlock();
    }

    public static void readD() {
        Data.d = Data.readScalar();
    }

    public static void readP() {
        Data.p = Data.readScalar();
    }

    public static void readZ(int n) {
        Data.Z = Data.readVector(n);
    }
}
