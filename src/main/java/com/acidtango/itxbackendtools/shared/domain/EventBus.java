package com.acidtango.itxbackendtools.shared.domain;

import java.util.List;

public interface EventBus {

    void publish(List<DomainEvent> events);

}
