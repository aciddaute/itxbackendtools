package com.acidtango.itxbackendtools.catalog.products.application;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.repository.MemoryProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void stores_newly_created_products_in_repository() {

        createProduct.run("Brand new t-shirt");
        List<Product> productsAfterFirstCreation = productsRepository.getProducts();

        createProduct.run("Red t-shirt");
        List<Product> productsAfterSecondCreation = productsRepository.getProducts();


        assertEquals(productsAfterFirstCreation.size(), 1);
        assertEquals(productsAfterSecondCreation.size(), 2);
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
