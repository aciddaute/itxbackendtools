package com.acidtango.itxbackendtools.catalog.sales.infrastructure;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.sales.domain.Sale;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleId;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleItem;
import com.acidtango.itxbackendtools.catalog.sales.infrastructure.repository.MongoSalesCrudRepository;
import com.acidtango.itxbackendtools.catalog.sales.infrastructure.repository.MongoSalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
public class MongoSalesRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private MongoSalesCrudRepository crudRepository;
    private MongoSalesRepository repository;

    @BeforeEach
    void init() {
        mongoTemplate.getCollection("sales").drop();
        repository = new MongoSalesRepository(crudRepository);
    }

    @Test
    void saves_sales_and_retrieves_them_by_id() {
        repository.save(Sale.createNew(SaleId.createNew(1), List.of(SaleItem.createNew(ProductId.createNew(1),
                ProductSize.S, 1))));
        repository.save(Sale.createNew(SaleId.createNew(2), List.of(SaleItem.createNew(ProductId.createNew(2),
                ProductSize.L, 3))));

        Sale first = repository.findById(SaleId.createNew(1));
        Sale second = repository.findById(SaleId.createNew(2));

        assertTrue(first.hasTotalUnits(1));
        assertTrue(second.hasTotalUnits(3));
    }

    @Test
    void retrieves_next_sale_id() {
        Integer firstResult = repository.getNextId();
        repository.save(Sale.createNew(SaleId.createNew(firstResult),
                List.of(SaleItem.createNew(ProductId.createNew(1), ProductSize.S, 1))));
        Integer secondResult = repository.getNextId();
        repository.save(Sale.createNew(SaleId.createNew(secondResult),
                List.of(SaleItem.createNew(ProductId.createNew(1), ProductSize.S, 1))));
        Integer thirdResult = repository.getNextId();


        assertEquals(firstResult, 1);
        assertEquals(secondResult, 2);
        assertEquals(thirdResult, 3);
    }
}
