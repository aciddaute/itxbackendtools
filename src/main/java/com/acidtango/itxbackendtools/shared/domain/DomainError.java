package com.acidtango.itxbackendtools.shared.domain;

public abstract class DomainError extends RuntimeException {
    public DomainError(String message) {
        super(message);
    }
}
