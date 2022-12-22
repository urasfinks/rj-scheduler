package ru.jamsys.scheduler;

import lombok.Setter;
import ru.jamsys.Procedure;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class SchedulerCustom extends AbstractScheduler {

    final CopyOnWriteArrayList<Procedure> list = new CopyOnWriteArrayList<>();
    @Setter
    Procedure lastProcedure = null; //Завершающий

    public SchedulerCustom(String name, long periodMillis) {
        super(name, periodMillis);
        run();
    }

    public void add(Procedure procedure) {
        if (procedure != null) {
            list.addIfAbsent(procedure);
        }
    }

    public void remove(Procedure procedure) {
        list.remove(procedure);
        if (list.isEmpty()) {
            shutdown();
        }
    }

    @Override
    public <T> Consumer<T> getConsumer() {
        return (t) -> {
            //System.out.println(Thread.currentThread().getName());
            list.forEach(Procedure::run);
            if (lastProcedure != null) {
                lastProcedure.run();
            }
        };
    }

}
