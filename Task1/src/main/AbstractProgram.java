package main;

import java.util.Random;

public class AbstractProgram extends Thread {
    private ProgramState state;
    private final Object stateLock;
    private volatile boolean terminated;
    private int updateCounter;

    public AbstractProgram() {
        this.state = ProgramState.UNKNOWN;
        this.stateLock = new Object();
        this.terminated = false;
        this.updateCounter = 0;
    }

    public void setState(ProgramState newState) {
        synchronized (stateLock) {
            if (terminated) return;
            this.state = newState;
            updateCounter++;
            LoggerConfig.logInfo("Abstract Program: состояние изменилось на " + newState);
            stateLock.notifyAll();
        }
    }

    public ProgramState getProgramState() {
        synchronized (stateLock) {
            return state;
        }
    }

    public int getUpdateCounter() {
        synchronized (stateLock) {
            return updateCounter;
        }
    }

    public Object getLock() {
        return stateLock;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    @Override
    public void run() {
        // Создаем демон-поток
        Thread randomDaemon = new Thread(() -> {
            Random random = new Random();

            while (!terminated && !Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    break;
                }

                if (terminated) break;

                int pick = random.nextInt(3);
                ProgramState newState = switch (pick) {
                    case 0 -> ProgramState.STOPPING;
                    case 1 -> ProgramState.FATAL_ERROR;
                    default -> ProgramState.RUNNING;
                };

                setState(newState);
            }
        });

        // Устанавливаем созданный поток как демон
        randomDaemon.setDaemon(true);
        randomDaemon.start();

        // Основной поток ждет завершения
        while (!isTerminated() && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }

        LoggerConfig.logInfo("Abstract Program: завершилась");
    }
}
