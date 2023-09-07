package com.acidtango.itxbackendtools.catalog.domain;

import com.acidtango.itxbackendtools.catalog.domain.primitives.ProductPrimitives;
import com.acidtango.itxbackendtools.shared.domain.AggregateRoot;

public class Product extends AggregateRoot {

    final ProductId id;

    final ProductName name;

    final ProductStock stock;

    private Product(ProductId id, ProductName name, ProductStock stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public static Product createNew(ProductId id, String name) {
        return new Product(id, ProductName.createNew(name), ProductStock.zero());
    }

    public static Product fromPrimitives(ProductPrimitives primitives) {
        return new Product(ProductId.fromPrimitives(primitives.id()), ProductName.fromPrimitives(primitives.name()), ProductStock.fromPrimitives(primitives.stock()));
    }

    public ProductPrimitives toPrimitives() {
        return new ProductPrimitives(id.getValue(), name.getValue(), stock.toPrimitives());
    }

    public ProductId getId() {
        return id;
    }

    public boolean hasName(String expectedName) {
        return this.name.getValue().equals(expectedName);
    }

    public boolean hasTotalStock(Integer expectedTotalStock) {
        return this.stock.hasTotalStock(expectedTotalStock);
    }
}
