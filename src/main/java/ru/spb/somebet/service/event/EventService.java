package ru.spb.somebet.service.event;

import ru.spb.somebet.model.Event;
import ru.spb.somebet.model.Result;

import java.util.Collection;

public interface EventService {

    Collection<Event> getFutureEvents();

    void saveEvent(Event event);

    Collection<Event> getLiveEvents();

    Collection<Result> getResults();
}
