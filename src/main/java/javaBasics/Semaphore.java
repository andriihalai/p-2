package javaBasics;

import java.util.LinkedList;
import java.util.Queue;

public class Semaphore {
    private int value;
    private final Queue<MainThread> queue;

    public Semaphore(int value)
    {
        this.value = value;
        this.queue = new LinkedList<>();
    }

    // Wait
    public void P(MainThread p)
    {
        value--;
        if (value < 0) {
            this.queue.add(p);
            p.block();
        }
    }

    // Signal
    public void V()
    {
        value++;
        if (value <= 0) {
            MainThread p = this.queue.remove();
            p.wakeup();
        }
    }
}
