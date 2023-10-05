package com.acidtango.itxbackendtools.catalog.sales.domain;

import com.acidtango.itxbackendtools.catalog.sales.domain.errors.SaleDuplicateItemsError;
import com.acidtango.itxbackendtools.catalog.sales.domain.events.SaleCreated;
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
        boolean duplicateItems =
                items.stream().map(SaleItem::getProductIdAndSizeIdentifier).distinct().count() != items.size();

        if (duplicateItems) {
            throw new SaleDuplicateItemsError();
        }

        Sale newSale = new Sale(id, items);
        newSale.register(new SaleCreated(id, items));
        return newSale;
    }

    public static Sale fromPrimitives(SalePrimitives primitives) {
        return new Sale(SaleId.fromPrimitives(primitives.id()),
                primitives.items().stream().map(SaleItem::fromPrimitives).toList());
    }

    public SalePrimitives toPrimitives() {
        return new SalePrimitives(id.getValue(), items.stream().map(SaleItem::toPrimitives).toList());
    }

    public SaleId getId() {
        return id;
    }


    public boolean hasTotalUnits(Integer expectedTotalUnits) {
        Integer totalUnits = items.stream().map(SaleItem::getAmount).reduce(Integer::sum).orElse(0);
        return totalUnits.equals(expectedTotalUnits);
    }
}
