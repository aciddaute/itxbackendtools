package com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;

import java.util.Map;

public record RestockProductRequestDto(Map<ProductSize, Integer> newUnits) {
}
