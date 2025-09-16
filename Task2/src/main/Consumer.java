package main;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class Consumer implements Runnable {
    private final BlockingQueue<String> queue;
    private final String consumerName;
    private final Logger logger;

    public Consumer(BlockingQueue<String> queue, int consumerId) {
        this.queue = queue;
        this.consumerName = "Consumer-" + consumerId;
        this.logger = LoggerConfig.getLogger();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = queue.take();

                if (MessageQueue.POISON_PILL.equals(message)) {
                    logger.info(consumerName + " получил poison pill - завершение работы");
                    break;
                }

                logger.info(consumerName + " получил: " + message);
            }
        } catch (InterruptedException e) {
            logger.severe(consumerName + " прерван: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
