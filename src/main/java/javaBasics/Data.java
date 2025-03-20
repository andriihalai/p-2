package javaBasics;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Data {
    private static final Random random = new Random();

    public static final int threadCount = 4;
    public static int N = 0;
    public static AtomicInteger d = new AtomicInteger();
    public static AtomicInteger p = new AtomicInteger();
    public static int[] Z;
    public static int[][] MC;
    public static int[][] MD;
    public static int[][] MA;
    public static int[][] MX;

    public static int z = Integer.MAX_VALUE;

    public static final Semaphore consoleSemaphore = new Semaphore(1);
    public static final Semaphore S2 = new Semaphore(1);
    public static final CyclicBarrier CL1 = new CyclicBarrier(Data.threadCount);
    private static final ReentrantLock L1 = new ReentrantLock();

    public static void setN(int N) {
        Data.N = N;
        Data.MA = new int[N][N];
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
        int temp = Data.readScalar();
        Data.d.set(temp);
    }

    public static void getRandomD() {
        int temp = getRandomInt(-1000, 1000);
        Data.d.set(temp);
    }

    public static void readP() {
        int temp = Data.readScalar();
        Data.p.set(temp);
    }

    public static void getRandomP() {
        int temp = getRandomInt(-1000, 1000);
        Data.p.set(temp);
    }

    public static void readZ(int n) {
        Data.Z = Data.readVector(n);
    }

    public static Size getSize(int N, int threadCount, int threadId) {
        int itemsCount = N / threadCount; // will be rounded to lower number
        int start = threadId * itemsCount;
        boolean isLastThread = (threadCount - 1) == threadId;
        int end = start + itemsCount;
        if (isLastThread) {
            int remainingItemsCount = N % threadCount;
            end += remainingItemsCount;
        }

        return new Size(start, end);
    }

    public static int[] getSubArr(int[] arr, int threadCount, int threadId) {
        Size size = Data.getSize(arr.length, threadCount, threadId);
        return Arrays.copyOfRange(arr, size.getStart(), size.getEnd());
    }

    public static void calculateRows(int threadCount, int threadId, int d, int z, int p) {
        Size size = Data.getSize(Data.N, threadCount, threadId);

        for (int i = size.getStart(); i < size.getEnd(); i++) {
            int[] res = new int[Data.N];
            for (int j = 0; j < Data.N; j++) {
                for (int k = 0; k < Data.N; k++) {
                    res[j] += Data.MD[k][j] * Data.MC[j][k];
                }
                System.out.println("z = " + z);
                System.out.println("res[i][j] = " + res[j] + " + " + z * Data.MX[i][j] * p);
                res[j] += z * Data.MX[i][j] * p;
            }
            Data.MA[i] = res;
        }
    }

    public static int getRandomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static int[] getRandomArray(int n, int min, int max) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = getRandomInt(min, max);
        }
        return arr;
    }

    public static int[][] getRandomMatrix(int n, int min, int max) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = getRandomInt(min, max);
            }
        }
        return matrix;
    }
}
