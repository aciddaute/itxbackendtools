package com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductReadModel;

public record ProductReadModelResponseDto(Integer id, String name, ProductStockResponseDto stock, Integer saleUnits,
                                          Double sortingScore) {

    public ProductReadModelResponseDto(ProductReadModel product) {
        this(product.id(), product.name(), new ProductStockResponseDto(product.stock()), product.saleUnits(),
                product.sortingScore());
    }

}