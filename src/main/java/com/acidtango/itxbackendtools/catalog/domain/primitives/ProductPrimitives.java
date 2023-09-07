package com.acidtango.itxbackendtools.catalog.domain.primitives;

import com.acidtango.itxbackendtools.catalog.domain.ProductSize;

import java.util.HashMap;

public record ProductPrimitives(Integer id, String name, HashMap<ProductSize, Integer> stock) {
}
