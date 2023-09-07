package com.acidtango.itxbackendtools.catalog.domain;

import com.acidtango.itxbackendtools.shared.domain.ValueObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductStock extends ValueObject {

    final HashMap<ProductSize, StockAmount> stock;

    private ProductStock(HashMap<ProductSize, StockAmount> stock) {
        this.stock = stock;
    }

    static ProductStock zero() {
        HashMap<ProductSize, StockAmount> stock = new HashMap<>();
        Arrays.asList(ProductSize.values()).forEach(size -> stock.put(size, StockAmount.zero()));

        return new ProductStock(stock);
    }

    static ProductStock fromPrimitives(HashMap<ProductSize, Integer> primitives) {

        HashMap<ProductSize, StockAmount> stock =
                primitives.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> StockAmount.fromPrimitives(entry.getValue()),
                        (firstEntry, secondEntry) -> firstEntry, HashMap::new));

        return new ProductStock(stock);
    }

    HashMap<ProductSize, Integer> toPrimitives() {
        return stock.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                entry -> entry.getValue().getValue(), (firstEntry, secondEntry) -> firstEntry, HashMap::new));
    }

    public boolean hasTotalStock(Integer expectedTotalStock) {
        Integer totalStock = stock.values().stream().map(StockAmount::getValue).reduce(Integer::sum).orElse(0);
        return totalStock.equals(expectedTotalStock);
    }
}
