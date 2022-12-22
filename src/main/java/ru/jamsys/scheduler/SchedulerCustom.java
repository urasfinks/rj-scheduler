package ru.jamsys.scheduler;

import ru.jamsys.Procedure;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class SchedulerCustom extends AbstractScheduler {

    final CopyOnWriteArrayList<Procedure> list = new CopyOnWriteArrayList<>();

    public SchedulerCustom(String name, long periodMillis) {
        super(name, periodMillis);
        run();
    }

    public void add(Procedure procedure) {
        list.addIfAbsent(procedure);
    }

    public void remove(Procedure procedure) {
        list.remove(procedure);
        if (list.isEmpty()) {
            shutdown();
        }
    }

    @Override
    public <T> Consumer<T> getConsumer() {
        return (t) -> list.forEach(Procedure::run);
    }

}
