package main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class Producer implements Runnable {
    private final BlockingQueue<String> queue;
    private final CountDownLatch latch;
    private final String producerName;
    private final Logger logger;

    public Producer(BlockingQueue<String> queue, CountDownLatch latch, int producerId) {
        this.queue = queue;
        this.latch = latch;
        this.producerName = "Producer-" + producerId;
        this.logger = LoggerConfig.getLogger();
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                String message = producerName + "-Message-" + i;
                queue.put(message);
                logger.info(producerName + " отправил: " + message);
            }

            // Каждый производитель отправляет один poison pill
            queue.put(MessageQueue.POISON_PILL);
            logger.info(producerName + " завершил работу");
        } catch (InterruptedException e) {
            logger.severe(producerName + " прерван: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown();
        }
    }
}
