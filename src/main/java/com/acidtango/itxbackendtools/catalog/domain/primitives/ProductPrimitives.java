package com.acidtango.itxbackendtools.catalog.domain.primitives;

import com.acidtango.itxbackendtools.catalog.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.domain.StockAmount;

import java.util.HashMap;

public record ProductPrimitives(Integer id, String name, HashMap<ProductSize, StockAmount> stock) {
}
