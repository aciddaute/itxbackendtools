package com.acidtango.itxbackendtools.catalog.domain;

import com.acidtango.itxbackendtools.catalog.domain.primitives.ProductStockPrimitives;
import com.acidtango.itxbackendtools.shared.domain.ValueObject;

public class ProductStock extends ValueObject {

    final StockAmount smallSizeStock;

    final StockAmount mediumSizeStock;

    final StockAmount largeSizeStock;

    private ProductStock(StockAmount smallSizeStock, StockAmount mediumSizeStock, StockAmount largeSizeStock) {
        this.smallSizeStock = smallSizeStock;
        this.mediumSizeStock = mediumSizeStock;
        this.largeSizeStock = largeSizeStock;
    }

    static ProductStock zero() {
        return new ProductStock(StockAmount.zero(), StockAmount.zero(), StockAmount.zero());
    }

    static ProductStock fromPrimitives(ProductStockPrimitives primitives) {
        return new ProductStock(StockAmount.fromPrimitives(primitives.smallUnits()), StockAmount.fromPrimitives(primitives.mediumUnits()), StockAmount.fromPrimitives(primitives.largeUnits()));
    }

    ProductStockPrimitives toPrimitives() {
        return new ProductStockPrimitives(smallSizeStock.toPrimitives(), mediumSizeStock.toPrimitives(), largeSizeStock.toPrimitives());
    }

}
