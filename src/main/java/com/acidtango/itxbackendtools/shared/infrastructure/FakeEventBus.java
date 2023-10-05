package com.acidtango.itxbackendtools.shared.infrastructure;

import com.acidtango.itxbackendtools.shared.domain.DomainEvent;
import com.acidtango.itxbackendtools.shared.domain.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeEventBus implements EventBus {

    List<DomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(List<DomainEvent> events) {
        publishedEvents.addAll(events);
    }

    public boolean nEventsHasBeenPublished(Integer expectedPublishedEventsCount) {
        return publishedEvents.size() == expectedPublishedEventsCount;
    }

    public <T extends DomainEvent> boolean eventHasBeenPublishedNTimes(Class<T> eventClass,
                                                                       Integer expectedPublishedEvents) {
        List<DomainEvent> filteredEvents =
                publishedEvents.stream().filter(event -> event.getClass() == eventClass).toList();

        return filteredEvents.size() == expectedPublishedEvents;
    }

    public <T extends DomainEvent> Optional<DomainEvent> getOlderEventOfType(Class<T> eventClass) {
        return publishedEvents.stream().filter(event -> event.getClass() == eventClass).findFirst();
    }
}
