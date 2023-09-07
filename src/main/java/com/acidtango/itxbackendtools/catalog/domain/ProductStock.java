package com.acidtango.itxbackendtools.catalog.domain;

import com.acidtango.itxbackendtools.shared.domain.ValueObject;

import java.util.Arrays;
import java.util.HashMap;

public class ProductStock extends ValueObject {

    final HashMap<ProductSize, StockAmount> stock;

    private ProductStock(HashMap<ProductSize, StockAmount> stock) {
        this.stock = stock;
    }

    static ProductStock zero() {
        HashMap<ProductSize, StockAmount> stock = new HashMap<>();
        Arrays.asList(ProductSize.values())
                .forEach(size -> stock.put(size, StockAmount.zero()));

        return new ProductStock(stock);
    }

    static ProductStock fromPrimitives(HashMap<ProductSize, StockAmount> primitives) {
        return new ProductStock(primitives);
    }

    HashMap<ProductSize, StockAmount> toPrimitives() {
        return stock;
    }

    public boolean hasTotalStock(Integer expectedTotalStock) {
        Integer totalStock = stock.values().stream().map(StockAmount::getValue).reduce(Integer::sum).orElse(0);
        
        return totalStock.equals(expectedTotalStock);
    }
}
