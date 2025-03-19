package javaBasics;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter N:");
        int N = Integer.parseInt(scanner.nextLine());
        Data.setN(N);
        Thread t1 = new Thread(new T1("T1", N, 0));
        Thread t2 = new Thread(new T2("T2", N, 1));
        Thread t3 = new Thread(new T3("T3", N, 2));
        Thread t4 = new Thread(new T4("T4", N, 3));

        t1.start();
        t2.start();
        t4.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            System.out.println(Arrays.deepToString(Data.MA));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}