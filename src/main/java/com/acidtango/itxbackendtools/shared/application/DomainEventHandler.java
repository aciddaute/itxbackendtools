package com.acidtango.itxbackendtools.shared.application;

import com.acidtango.itxbackendtools.shared.domain.DomainEvent;
import org.springframework.context.event.EventListener;

public abstract class DomainEventHandler<D extends DomainEvent> {

    public abstract void handle(D event);


    @EventListener
    private void onEvent(D event) {
        handle(event);
    }
}
