package com.acidtango.itxbackendtools.catalog.products.domain;

import com.acidtango.itxbackendtools.catalog.products.domain.primitives.ProductPrimitives;

import java.util.Map;

public record ProductReadModel(Integer id, String name, Map<ProductSize, Integer> stock, Integer salesUnits,
                               Double sortingScore) {


    public ProductReadModel(ProductPrimitives primitives, Double sortingScore) {
        this(primitives.id(), primitives.name(), primitives.stock(), primitives.saleUnits(), sortingScore);
    }

}
