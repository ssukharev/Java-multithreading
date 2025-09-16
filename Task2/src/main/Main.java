package main;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerConfig.getLogger();

        if (args.length != 1) {
            logger.severe("Неверное количество аргументов. Использование: java Main <количество_потоков>");
            return;
        }

        int threadCount;
        try {
            threadCount = Integer.parseInt(args[0]);
            if (threadCount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            logger.severe("Ошибка: Некорректное значение количества потоков");
            return;
        }



        // Создание очереди сообщений
        MessageQueue messageQueue = new MessageQueue();
        CountDownLatch producerLatch = new CountDownLatch(threadCount);

        logger.info("Запуск приложения с " + threadCount + " потоками производителей и потребителей");

        // Создание и запуск потоков с ExecutorService
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount * 2);

        // Запуск производителей
        for (int i = 1; i <= threadCount; i++) {
            executorService.submit(new Producer(messageQueue.getQueue(), producerLatch, i));
        }

        // Запуск потребителей
        for (int i = 1; i <= threadCount; i++) {
            executorService.submit(new Consumer(messageQueue.getQueue(), i));
        }

        logger.info("Все потоки запущены");

        // Завершение ExecutorService
        executorService.shutdown();
    }
}
