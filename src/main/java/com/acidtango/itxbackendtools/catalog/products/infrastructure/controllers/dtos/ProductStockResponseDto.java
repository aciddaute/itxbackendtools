package com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;

import java.util.Map;

public record ProductStockResponseDto(Integer smallUnits, Integer mediumUnits, Integer largeUnits) {

    public ProductStockResponseDto(Map<ProductSize, Integer> stock) {
        this(stock.get(ProductSize.S), stock.get(ProductSize.M), stock.get(ProductSize.L));
    }
}