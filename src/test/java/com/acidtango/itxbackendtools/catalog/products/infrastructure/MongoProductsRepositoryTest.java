package com.acidtango.itxbackendtools.catalog.products.infrastructure;


import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories.MongoProductsCrudRepository;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories.MongoProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
public class MongoProductsRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private MongoProductsCrudRepository crudRepository;
    private MongoProductsRepository repository;

    @BeforeEach
    void init() {
        mongoTemplate.dropCollection("products");
        repository = new MongoProductsRepository(crudRepository);
    }

    @Test
    void saves_products_and_retrieves_them_by_id() {
        repository.save(Product.createNew(ProductId.createNew(1), "Basic t-shirt"));
        repository.save(Product.createNew(ProductId.createNew(2), "Fabric t-shirt"));

        Product first = repository.findById(ProductId.createNew(1));
        Product second = repository.findById(ProductId.createNew(2));

        assertTrue(first.hasName("BASIC T-SHIRT"));
        assertTrue(second.hasName("FABRIC T-SHIRT"));
    }

    @Test
    void retrieves_all_products() {
        repository.save(Product.createNew(ProductId.createNew(1), "Basic black t-shirt"));
        repository.save(Product.createNew(ProductId.createNew(2), "Basic green t-shirt"));
        repository.save(Product.createNew(ProductId.createNew(3), "Basic blue t-shirt"));
        repository.save(Product.createNew(ProductId.createNew(4), "Basic pink t-shirt"));
        repository.save(Product.createNew(ProductId.createNew(5), "Basic yellow t-shirt"));

        List<Product> allProducts = repository.getProducts();

        assertEquals(allProducts.size(), 5);
    }

    @Test
    void retrieves_next_product_id() {
        Integer firstResult = repository.getNextId();
        repository.save(Product.createNew(ProductId.createNew(firstResult), "Basic red t-shirt"));
        Integer secondResult = repository.getNextId();
        repository.save(Product.createNew(ProductId.createNew(secondResult), "Basic blue t-shirt"));
        Integer thirdResult = repository.getNextId();


        assertEquals(firstResult, 1);
        assertEquals(secondResult, 2);
        assertEquals(thirdResult, 3);
    }
}
