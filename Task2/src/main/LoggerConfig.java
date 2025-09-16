package main;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    private static Logger logger;

    public static Logger getLogger() {
        if (logger == null) {
            setupLogger();
        }
        return logger;
    }

    private static void setupLogger() {
        logger = Logger.getLogger("MessageQueue");

        try {
            FileHandler fileHandler = new FileHandler("message_queue.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);

            logger.info("Logger настроен успешно");
        } catch (IOException e) {
            System.err.println("Ошибка настройки логгера: " + e.getMessage());
        }
    }
}
