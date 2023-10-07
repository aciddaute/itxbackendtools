package com.acidtango.itxbackendtools.catalog.sales.infrastructure.repositories;

import com.acidtango.itxbackendtools.catalog.sales.domain.Sale;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleId;
import com.acidtango.itxbackendtools.catalog.sales.domain.SalesRepository;
import com.acidtango.itxbackendtools.catalog.sales.domain.errors.SaleDoesNotExistError;
import com.acidtango.itxbackendtools.catalog.sales.domain.primitives.SalePrimitives;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;


@Component
@Profile("memory")
public class MemorySalesRepository implements SalesRepository {

    public final HashMap<Integer, SalePrimitives> sales = new HashMap<>();

    @Override
    public void save(Sale sale) {
        SalePrimitives primitives = sale.toPrimitives();
        sales.put(primitives.id(), primitives);
    }

    @Override
    public Integer getNextId() {
        return sales.size() + 1;
    }

    @Override
    public Sale findById(SaleId saleId) {
        Optional<SalePrimitives> primitives = Optional.ofNullable(sales.get(saleId.getValue()));

        return Sale.fromPrimitives(primitives.orElseThrow(() -> new SaleDoesNotExistError(saleId)));
    }
}
