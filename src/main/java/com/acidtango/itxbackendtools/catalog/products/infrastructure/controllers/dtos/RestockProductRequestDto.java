package com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;

import java.util.HashMap;

public record RestockProductRequestDto(HashMap<ProductSize, Integer> newUnits) {
}
