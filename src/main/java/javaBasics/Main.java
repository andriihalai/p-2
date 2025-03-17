package javaBasics;

public class Main {
    public static void main(String[] args) {
        int N = 10;
        Data.setN(N);
        Thread t1 = new Thread(new T1("T1", N));
        Thread t2 = new Thread(new T2("T2", N));
        Thread t3 = new Thread(new T3("T3", N));
        Thread t4 = new Thread(new T4("T4", N));

        t1.start();
        t2.start();
        t4.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("z: " + Data.z);
    }
}