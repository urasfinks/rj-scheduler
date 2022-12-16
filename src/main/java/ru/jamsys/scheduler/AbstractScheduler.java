package ru.jamsys.scheduler;

import lombok.Getter;
import lombok.Setter;
import ru.jamsys.thread.CustomThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public abstract class AbstractScheduler implements Scheduler {

    @Setter
    protected boolean debug = false;

    private final ScheduledExecutorService executor;
    private final AtomicBoolean isRun = new AtomicBoolean(false);

    @Getter
    private final String name;
    @Getter
    private final long periodMillis;

    public AbstractScheduler(String name, long periodMillis) {
        this.name = name;
        this.periodMillis = periodMillis;
        executor = Executors.newScheduledThreadPool(1, new CustomThreadFactory(getName()));
    }

    public void run() {
        run(null);
    }

    public <T> void run(T t) {
        if (isRun.compareAndSet(false, true)) {
            executor.scheduleAtFixedRate(() -> getConsumer().accept(t), 1, getPeriodMillis(), TimeUnit.MILLISECONDS);
        }
    }

    public void shutdown() {
        if (isRun.get()) {
            executor.shutdownNow();
            isRun.set(false);
        }
    }

}
