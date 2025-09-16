package main;

import java.util.logging.*;
import java.io.IOException;

public class LoggerConfig {
    private static final Logger logger = Logger.getLogger("ProgramLogger");

    static {
        try {
            // Настройка обработчика для файла
            FileHandler fh = new FileHandler("program.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);

            // Настройка обработчика для консоли
            ConsoleHandler ch = new ConsoleHandler();
            ch.setFormatter(new SimpleFormatter());
            logger.addHandler(ch);

            // Отключаем родительские обработчики для избежания дублирования
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logWarning(String message) {
        logger.warning(message);
    }

    public static void logSevere(String message) {
        logger.severe(message);
    }
}
