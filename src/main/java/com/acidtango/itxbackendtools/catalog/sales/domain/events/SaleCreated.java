package com.acidtango.itxbackendtools.catalog.sales.domain.events;

import com.acidtango.itxbackendtools.catalog.sales.domain.SaleId;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleItem;
import com.acidtango.itxbackendtools.catalog.sales.domain.primitives.SaleItemPrimitives;
import com.acidtango.itxbackendtools.shared.domain.DomainEvent;

import java.util.List;

public class SaleCreated extends DomainEvent {

    final public Integer id;

    final public List<SaleItemPrimitives> items;

    public SaleCreated(SaleId id, List<SaleItem> items) {
        this.id = id.getValue();
        this.items = items.stream().map(SaleItem::toPrimitives).toList();
    }
}
