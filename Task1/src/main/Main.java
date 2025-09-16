package main;

public class Main {
    public static void main(String[] args) {
        Object mainLock = new Object();
        AbstractProgram program = new AbstractProgram();
        Supervisor supervisor = new Supervisor(program, mainLock);

        supervisor.start();
        program.start();

        try {
            synchronized (mainLock) {
                mainLock.wait(); // Ожидаем уведомления о завершении
            }
            LoggerConfig.logInfo("Main: получено уведомление о завершении программы");
        } catch (InterruptedException e) {
            LoggerConfig.logWarning("Main: основной поток был прерван");
            Thread.currentThread().interrupt();
        }

        LoggerConfig.logInfo("Main: программа завершена");
    }
}
