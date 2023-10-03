package com.acidtango.itxbackendtools.catalog.sales.domain;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;

public record RequiredStock(ProductId productId, ProductSize size, Integer amount) {
}
