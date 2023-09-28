package com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos;

import com.acidtango.itxbackendtools.catalog.products.domain.primitives.ProductPrimitives;

public record ProductResponseDto(Integer id, String name, ProductStockResponseDto stock) {

    public ProductResponseDto(ProductPrimitives product) {
        this(product.id(), product.name(), new ProductStockResponseDto(product.stock()));
    }
}
