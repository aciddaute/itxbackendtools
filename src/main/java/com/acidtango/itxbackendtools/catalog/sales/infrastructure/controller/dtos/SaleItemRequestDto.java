package com.acidtango.itxbackendtools.catalog.sales.infrastructure.controller.dtos;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;

public record SaleItemRequestDto(Integer productId, ProductSize size, Integer amount) {
}
