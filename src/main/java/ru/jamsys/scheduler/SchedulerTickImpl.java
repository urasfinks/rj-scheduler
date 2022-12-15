package ru.jamsys.scheduler;

import java.util.function.Consumer;

public class SchedulerTickImpl extends AbstractScheduler {

    public SchedulerTickImpl(String name, int periodMillis) {
        super(name, periodMillis);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Consumer<SchedulerTick> getConsumer() {
        return SchedulerTick::tick;
    }

}
