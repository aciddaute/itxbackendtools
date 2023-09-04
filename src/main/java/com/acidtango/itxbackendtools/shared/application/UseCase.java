package com.acidtango.itxbackendtools.shared.application;

public abstract class UseCase<P, R> {

    public abstract R run(P params);

}
