package com.acidtango.itxbackendtools.shared.infrastructure;

import com.acidtango.itxbackendtools.shared.domain.DomainEvent;
import com.acidtango.itxbackendtools.shared.domain.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringEventBus implements EventBus {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(List<DomainEvent> events) {
        for (DomainEvent event : events) {
            applicationEventPublisher.publishEvent(event);
        }
    }
}
