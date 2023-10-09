package com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos;

import java.util.List;

public record ListProductsResponseDto(List<ProductReadModelResponseDto> products) {
}
