package javaBasics;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainThread {
    private final String name;
    public final int N;
    public final int threadId;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public MainThread(String name, int N, int threadId) {
        this.name = name;
        this.N = N;
        this.threadId = threadId;
    }

    public String getName() {
        return "[" + name + "]";
    }
}
