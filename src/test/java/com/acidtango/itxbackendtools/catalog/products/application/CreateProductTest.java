package com.acidtango.itxbackendtools.catalog.products.application;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories.MemoryProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateProductTest {

    private ProductsRepository productsRepository = new MemoryProductsRepository();

    private CreateProduct createProduct = new CreateProduct(productsRepository);

    @BeforeEach
    void init() {
        productsRepository = new MemoryProductsRepository();
        createProduct = new CreateProduct(productsRepository);
    }


    @Test
    void creates_and_persist_product() {
        ProductId productId = createProduct.run("Brand new t-shirt");

        Product product = productsRepository.findById(productId);

        assertNotNull(product);
    }

    @Test
    void products_names_are_stored_capitalized() {
        String productName = "Brand new t-shirt";

        ProductId newProductId = createProduct.run(productName);

        Product newProduct = productsRepository.findById(newProductId);
        assertTrue(newProduct.hasName("BRAND NEW T-SHIRT"));
    }

    @Test
    void products_are_created_with_zero_stock() {
        ProductId newProductId = createProduct.run("Brandy t-shirt");

        Product newProduct = productsRepository.findById(newProductId);
        assertTrue(newProduct.hasTotalStock(0));
    }
}
