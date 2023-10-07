package com.acidtango.itxbackendtools.catalog.sales.infrastructure.repositories;

import com.acidtango.itxbackendtools.catalog.sales.domain.Sale;
import com.acidtango.itxbackendtools.catalog.sales.domain.primitives.SaleItemPrimitives;
import com.acidtango.itxbackendtools.catalog.sales.domain.primitives.SalePrimitives;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("sales")
public class SaleDocument {

    @Id
    Integer id;

    List<SaleItemPrimitives> items;

    private SaleDocument(Integer id, List<SaleItemPrimitives> items) {
        this.id = id;
        this.items = items;
    }

    static SaleDocument fromDomain(Sale sale) {
        SalePrimitives primitives = sale.toPrimitives();
        return new SaleDocument(primitives.id(), primitives.items());
    }

    public Sale toDomain() {
        return Sale.fromPrimitives(new SalePrimitives(id, items));
    }
}
