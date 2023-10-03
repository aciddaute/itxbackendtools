package com.acidtango.itxbackendtools.catalog.products.domain;

import com.acidtango.itxbackendtools.catalog.products.domain.errors.ProductOutOfStockError;
import com.acidtango.itxbackendtools.catalog.products.domain.primitives.ProductPrimitives;
import com.acidtango.itxbackendtools.catalog.sales.domain.RequiredStock;
import com.acidtango.itxbackendtools.shared.domain.AggregateRoot;

import java.util.Map;

public class Product extends AggregateRoot {

    final ProductId id;

    final ProductName name;

    ProductStock stock;

    private Product(ProductId id, ProductName name, ProductStock stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public static Product createNew(ProductId id, String name) {
        return new Product(id, ProductName.createNew(name), ProductStock.zero());
    }

    public static Product fromPrimitives(ProductPrimitives primitives) {
        return new Product(ProductId.fromPrimitives(primitives.id()), ProductName.fromPrimitives(primitives.name()),
                ProductStock.fromPrimitives(primitives.stock()));
    }

    public ProductPrimitives toPrimitives() {
        return new ProductPrimitives(id.getValue(), name.getValue(), stock.toPrimitives());
    }

    public ProductId getId() {
        return id;
    }

    public boolean hasName(String expectedName) {
        return name.getValue().equals(expectedName);
    }

    public boolean hasTotalStock(Integer expectedStock) {
        return stock.hasTotalStock(expectedStock);
    }

    public boolean hasSizeStock(ProductSize size, Integer expectedStock) {
        return stock.hasSizeStock(size, expectedStock);
    }

    public void restock(Map<ProductSize, Integer> newUnits) {
        stock = stock.restock(newUnits);
    }

    public void ensureStockAvailability(RequiredStock requiredStock) {
        boolean enoughStock = stock.hasEnoughStock(requiredStock);
        if (!enoughStock) {
            throw new ProductOutOfStockError(id, requiredStock.size());
        }
    }
}
