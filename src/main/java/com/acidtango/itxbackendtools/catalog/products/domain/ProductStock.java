package com.acidtango.itxbackendtools.catalog.products.domain;

import com.acidtango.itxbackendtools.catalog.sales.domain.RequiredStock;
import com.acidtango.itxbackendtools.shared.domain.ValueObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductStock extends ValueObject {

    final Map<ProductSize, StockAmount> stock;

    private ProductStock(Map<ProductSize, StockAmount> stock) {
        this.stock = stock;
    }

    static ProductStock zero() {
        Map<ProductSize, StockAmount> stock = new HashMap<>();

        return new ProductStock(stock);
    }

    static ProductStock fromPrimitives(Map<ProductSize, Integer> primitives) {
        Map<ProductSize, StockAmount> stock =
                primitives.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> StockAmount.fromPrimitives(entry.getValue()),
                        (firstEntry, secondEntry) -> firstEntry, HashMap::new));

        return new ProductStock(stock);
    }

    private StockAmount getStockFor(ProductSize size) {
        return Optional.ofNullable(stock.get(size)).orElse(StockAmount.zero());
    }

    Map<ProductSize, Integer> toPrimitives() {
        return stock.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                entry -> entry.getValue().getValue(), (firstEntry, secondEntry) -> firstEntry, HashMap::new));
    }

    public boolean hasTotalStock(Integer expectedTotalStock) {
        Integer totalStock = stock.values().stream().map(StockAmount::getValue).reduce(Integer::sum).orElse(0);
        return totalStock.equals(expectedTotalStock);
    }

    public boolean hasSizeStock(ProductSize size, Integer expectedStock) {
        return getStockFor(size).getValue().equals(expectedStock);
    }

    public ProductStock restock(Map<ProductSize, Integer> restockUnits) {
        Map<ProductSize, StockAmount> updatedStock = new HashMap<>();

        restockUnits.forEach((size, newUnits) -> updatedStock.put(size, getStockFor(size).restock(newUnits)));
        this.stock.keySet().stream().filter((size) -> !updatedStock.containsKey(size)).forEach(size -> updatedStock.put(size, getStockFor(size)));

        return new ProductStock(updatedStock);
    }

    public boolean hasEnoughStock(RequiredStock requiredStock) {
        Integer sizeStock = getStockFor(requiredStock.size()).getValue();

        return requiredStock.amount() <= sizeStock;
    }
}
