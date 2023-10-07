package com.acidtango.itxbackendtools.catalog.products.domain;

import com.acidtango.itxbackendtools.shared.domain.ValueObject;

public class SaleUnits extends ValueObject {
    private final Integer units;

    private SaleUnits(Integer units) {
        this.units = units;
    }

    public static SaleUnits zero() {
        return new SaleUnits(0);
    }

    public static SaleUnits fromPrimitives(Integer units) {
        return new SaleUnits(units);
    }

    Integer getValue() {
        return units;
    }

    public SaleUnits add(Integer newSaleUnits) {
        return new SaleUnits(this.units + newSaleUnits);
    }
}
