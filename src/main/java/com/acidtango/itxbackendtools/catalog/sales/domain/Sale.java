package com.acidtango.itxbackendtools.catalog.sales.domain;

import com.acidtango.itxbackendtools.catalog.sales.domain.primitives.SalePrimitives;
import com.acidtango.itxbackendtools.shared.domain.AggregateRoot;

import java.util.List;

public class Sale extends AggregateRoot {
    private final SaleId id;

    private final List<SaleItem> items;

    private Sale(SaleId id, List<SaleItem> items) {
        this.id = id;
        this.items = items;
    }

    public static Sale createNew(SaleId id, List<SaleItem> items) {
        return new Sale(id, items);
    }

    public static Sale fromPrimitives(SalePrimitives primitives) {
        return new Sale(SaleId.fromPrimitives(primitives.id()),
                primitives.items().stream().map(SaleItem::fromPrimitives).toList());
    }

    public SalePrimitives toPrimitives() {
        return new SalePrimitives(id.getValue(), items.stream().map(SaleItem::toPrimitives).toList());
    }
}
