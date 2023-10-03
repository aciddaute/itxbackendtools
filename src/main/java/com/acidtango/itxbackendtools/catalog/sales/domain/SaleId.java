package com.acidtango.itxbackendtools.catalog.sales.domain;

import com.acidtango.itxbackendtools.shared.domain.ValueObject;

public class SaleId extends ValueObject {
    private final Integer id;

    private SaleId(Integer id) {
        this.id = id;
    }

    public static SaleId createNew(Integer id) {
        return new SaleId(id);
    }

    public static SaleId fromPrimitives(Integer id) {
        return new SaleId(id);
    }

    public Integer getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SaleId other)) {
            return false;
        }

        return id.equals(other.getValue());
    }
}


