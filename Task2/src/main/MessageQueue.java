package main;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueue {
    private final BlockingQueue<String> queue;
    public static final String POISON_PILL = "POISON_PILL";

    public MessageQueue() {
        this.queue = new ArrayBlockingQueue<>(100);
    }

    public BlockingQueue<String> getQueue() {
        return queue;
    }
}
