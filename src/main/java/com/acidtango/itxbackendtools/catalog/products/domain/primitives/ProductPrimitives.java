package com.acidtango.itxbackendtools.catalog.products.domain.primitives;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;

import java.util.Map;

public record ProductPrimitives(Integer id, String name, Map<ProductSize, Integer> stock, Integer saleUnits) {
}
