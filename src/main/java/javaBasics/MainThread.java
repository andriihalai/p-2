package javaBasics;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainThread {
    private final String name;
    public final int N;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean isBlocked = false;

    public MainThread(String name, int N) {
        this.name = name;
        this.N = N;
    }

    public String getName() {
        return "[" + name + "]";
    }

    public void block() {
        lock.lock();
        try {
            isBlocked = true;
            condition.await(); // Block the thread
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        } finally {
            lock.unlock();
        }
    }

    public void wakeup() {
        lock.lock();
        try {
            isBlocked = false;
            condition.signal(); // Wake up a blocked thread
        } finally {
            lock.unlock();
        }
    }
}
