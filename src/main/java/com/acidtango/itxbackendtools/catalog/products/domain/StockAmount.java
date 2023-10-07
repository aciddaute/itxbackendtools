package com.acidtango.itxbackendtools.catalog.products.domain;

import com.acidtango.itxbackendtools.catalog.products.domain.errors.NegativeRestockUnitsError;
import com.acidtango.itxbackendtools.shared.domain.ValueObject;

public class StockAmount extends ValueObject {

    private final Integer units;

    private StockAmount(Integer units) {
        this.units = units;
    }

    public static StockAmount zero() {
        return new StockAmount(0);
    }

    public static StockAmount fromPrimitives(Integer units) {
        return new StockAmount(units);
    }

    Integer getValue() {
        return units;
    }

    public StockAmount restock(Integer restockUnits) {

        if (restockUnits < 0) {
            throw new NegativeRestockUnitsError(restockUnits);
        }

        return new StockAmount(this.units + restockUnits);
    }

    public StockAmount adjustAfterSale(Integer saleUnits) {
        return new StockAmount(this.units - saleUnits);
    }
}
