package com.acidtango.itxbackendtools.catalog.products.domain;

import com.acidtango.itxbackendtools.shared.domain.ValueObject;

public class ProductId extends ValueObject {

    private final Integer id;

    private ProductId(Integer id) {
        this.id = id;
    }

    public static ProductId createNew(Integer id) {
        return new ProductId(id);
    }

    public static ProductId fromPrimitives(Integer id) {
        return new ProductId(id);
    }

    public Integer getValue() {
        return id;
    }
}
