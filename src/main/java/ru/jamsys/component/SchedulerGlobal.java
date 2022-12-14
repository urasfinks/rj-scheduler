package ru.jamsys.component;

import org.springframework.stereotype.Component;
import ru.jamsys.Procedure;
import ru.jamsys.scheduler.AbstractScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class SchedulerGlobal extends AbstractScheduler {

    final List<Procedure> list = new ArrayList<>();

    public SchedulerGlobal() {
        super("SchedulerGlobal", 1000);
        run();
    }

    public void add(Procedure procedure) {
        list.add(procedure);
    }

    @Override
    public <T> Consumer<T> getConsumer() {
        return (t) -> {
            list.stream().forEach(procedure -> {
                procedure.run();
            });
        };
    }

}
