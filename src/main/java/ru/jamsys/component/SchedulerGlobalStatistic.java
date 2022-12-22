package ru.jamsys.component;

import org.springframework.stereotype.Component;
import ru.jamsys.Procedure;
import ru.jamsys.scheduler.AbstractScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class SchedulerGlobalStatistic extends AbstractScheduler {

    final List<Procedure> list = new ArrayList<>();

    public SchedulerGlobalStatistic() {
        super("SchedulerGlobalStatistic", 1000);
        run();
    }

    @SuppressWarnings("unused")
    public void add(Procedure procedure) {
        list.add(procedure);
    }

    @SuppressWarnings("unused")
    public void remove(Procedure procedure){
        list.remove(procedure);
    }

    @Override
    public <T> Consumer<T> getConsumer() {
        return (t) -> list.forEach(Procedure::run);
    }

}
