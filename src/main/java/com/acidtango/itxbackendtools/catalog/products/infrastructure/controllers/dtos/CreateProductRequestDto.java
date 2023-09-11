package com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateProductRequestDto(@NotBlank() String productName) {
}
