package com.acidtango.itxbackendtools.catalog.sales.domain;

public interface SalesRepository {

    void save(Sale sale);

    Integer getNextId();

    Sale findById(SaleId id);
}
