package com.acidtango.itxbackendtools.catalog.sales.domain.primitives;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;

public record SaleItemPrimitives(Integer productId, ProductSize productSize, Integer amount) {
}
