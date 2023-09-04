package com.acidtango.itxbackendtools.shared.application;

import com.acidtango.itxbackendtools.shared.domain.DomainEvent;

public abstract class DomainEventHandler<D extends DomainEvent> {

    public abstract void handle(D event);

}
