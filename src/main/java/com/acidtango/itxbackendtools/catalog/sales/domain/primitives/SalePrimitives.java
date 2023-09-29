package com.acidtango.itxbackendtools.catalog.sales.domain.primitives;

import java.util.List;

public record SalePrimitives(Integer id, List<SaleItemPrimitives> items) {
}
