package com.acidtango.itxbackendtools.catalog.domain;

import com.acidtango.itxbackendtools.shared.domain.ValueObject;

public class ProductName extends ValueObject {

    private final String name;

    private ProductName(String name) {
        this.name = name;
    }

    public static ProductName createNew(String name) {
        return new ProductName(name.toUpperCase());
    }

    public static ProductName fromPrimitives(String name) {
        return new ProductName(name);
    }

    String getValue() {
        return name;
    }

}
