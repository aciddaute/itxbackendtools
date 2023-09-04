package com.acidtango.itxbackendtools.shared.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

    private List<DomainEvent> events = new ArrayList<>();

    final protected void register(DomainEvent event) {
        events.add(event);
    }

    final public List<DomainEvent> getRegisteredDomainEvents() {
        List<DomainEvent> registeredEvents = this.events;
        this.events = new ArrayList<>();

        return registeredEvents;
    }
}
