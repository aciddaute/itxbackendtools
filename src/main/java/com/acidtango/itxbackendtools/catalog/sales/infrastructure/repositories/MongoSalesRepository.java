package com.acidtango.itxbackendtools.catalog.sales.infrastructure.repositories;


import com.acidtango.itxbackendtools.catalog.sales.domain.Sale;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleId;
import com.acidtango.itxbackendtools.catalog.sales.domain.SalesRepository;
import com.acidtango.itxbackendtools.catalog.sales.domain.errors.SaleDoesNotExistError;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Profile("mongo")
public class MongoSalesRepository implements SalesRepository {

    private final MongoSalesCrudRepository crudRepository;

    public MongoSalesRepository(MongoSalesCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public void save(Sale sale) {
        crudRepository.save(SaleDocument.fromDomain(sale));
    }

    @Override
    public Integer getNextId() {
        return Math.toIntExact(crudRepository.count()) + 1;
    }

    @Override
    public Sale findById(SaleId id) {
        Optional<SaleDocument> document = crudRepository.findById(id.getValue());

        if (document.isPresent()) {
            return document.get().toDomain();
        }

        throw new SaleDoesNotExistError(id);
    }
}
