package com.acidtango.itxbackendtools.catalog.sales.infrastructure.controller.dtos;

import java.util.List;

public record CreateSaleRequestDto(List<SaleItemRequestDto> saleItems) {
}
