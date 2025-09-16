package main;

public class Supervisor extends Thread {
    private AbstractProgram program;
    private int lastUpdate;
    private boolean fterm = false;
    private final Object mainLock;


    public Supervisor(AbstractProgram program, Object mainLock) {
        this.program = program;
        this.lastUpdate = -1;
        this.mainLock = mainLock;
    }

    @Override
    public void run() {
        LoggerConfig.logInfo("Supervisor: начал работу");
        LoggerConfig.logInfo("Abstract Program запущена, начальное состояние: " + program.getProgramState());

        while (!fterm) {
            synchronized (program.getLock()) {
                while (program.getUpdateCounter() == lastUpdate && !program.isTerminated()) {
                    try {
                        program.getLock().wait();
                    } catch (InterruptedException e) {
                        LoggerConfig.logWarning("Supervisor: поток прерван во время ожидания обновления состояния.");
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                lastUpdate = program.getUpdateCounter();
                ProgramState currentState = program.getProgramState();
                LoggerConfig.logInfo("Supervisor: обнаружено новое состояние программы: " + currentState);

                if (currentState == ProgramState.STOPPING) {
                    LoggerConfig.logInfo("Supervisor: обнаружено состояние STOPPING, выполняется перезапуск...");
                    restart();
                } else if (currentState == ProgramState.FATAL_ERROR) {
                    LoggerConfig.logSevere("Supervisor: обнаружена критическая ошибка (FATAL_ERROR). Завершение работы программы.");
                    terminate();
                    fterm = true;

                    // Уведомляем главный поток о завершении
                    synchronized (mainLock) {
                        mainLock.notifyAll();
                    }
                } else if (currentState == ProgramState.RUNNING) {
                    LoggerConfig.logInfo("Supervisor: обнаружено состояние RUNNING");
                }
            }
        }

        LoggerConfig.logInfo("Supervisor: завершил работу");
        LoggerConfig.logInfo("Abstract Program завершила работу.");
    }

    public void restart() {
        LoggerConfig.logInfo("Supervisor: выполняется перезапуск программы...");

        program.setTerminated(true);


        // Ждем завершения старого потока
        try {
            if (program.isAlive()) {
                program.join(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        // Создаем новый демон-поток
        AbstractProgram newProgram = new AbstractProgram();
        this.program = newProgram;
        this.lastUpdate = -1;

        newProgram.start();
        newProgram.setState(ProgramState.RUNNING);
        LoggerConfig.logInfo("Supervisor: программа перезапущена, новое состояние: " + newProgram.getProgramState());
    }

    public void terminate() {
        program.setTerminated(true);
    }
}
