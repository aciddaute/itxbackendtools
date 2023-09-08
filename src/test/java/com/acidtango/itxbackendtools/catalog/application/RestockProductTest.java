package com.acidtango.itxbackendtools.catalog.application;

import com.acidtango.itxbackendtools.catalog.domain.Product;
import com.acidtango.itxbackendtools.catalog.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.domain.errors.NegativeRestockUnits;
import com.acidtango.itxbackendtools.catalog.domain.errors.ProductDoesNotExist;
import com.acidtango.itxbackendtools.catalog.infrastructure.repository.MemoryProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestockProductTest {

    private ProductsRepository productsRepository = new MemoryProductsRepository();

    private RestockProduct restockProduct = new RestockProduct(productsRepository);

    @BeforeEach
    void init() {
        productsRepository = new MemoryProductsRepository();
        restockProduct = new RestockProduct(productsRepository);
    }

    ProductId createProduct() {
        Integer nextProductId = productsRepository.getNextId();
        Product newProduct = Product.createNew(ProductId.createNew(nextProductId), "Test product");

        productsRepository.save(newProduct);

        return newProduct.getId();
    }

    ProductId createProductWithPreviousStock() {
        ProductId productId = createProduct();
        Product product = productsRepository.findById(productId);

        HashMap<ProductSize, Integer> previousStock = new HashMap<>();
        previousStock.put(ProductSize.S, 4);
        previousStock.put(ProductSize.M, 1);
        previousStock.put(ProductSize.L, 2);

        product.restock(previousStock);

        productsRepository.save(product);

        return productId;
    }

    @Test
    void restocks_a_product_with_zero_stock() {
        ProductId productId = createProduct();
        HashMap<ProductSize, Integer> restockUnits = new HashMap<>();
        restockUnits.put(ProductSize.S, 5);
        restockUnits.put(ProductSize.M, 10);
        restockUnits.put(ProductSize.L, 3);

        restockProduct.run(productId, restockUnits);

        Product restockedProduct = productsRepository.findById(productId);

        assertTrue(restockedProduct.hasTotalStock(18));
        assertTrue(restockedProduct.hasSizeStock(ProductSize.S, 5));
        assertTrue(restockedProduct.hasSizeStock(ProductSize.M, 10));
        assertTrue(restockedProduct.hasSizeStock(ProductSize.L, 3));
    }

    @Test
    void restocks_a_product_with_previous_stock() {
        ProductId productId = createProductWithPreviousStock();

        HashMap<ProductSize, Integer> restockUnits = new HashMap<>();
        restockUnits.put(ProductSize.S, 4);
        restockUnits.put(ProductSize.M, 7);
        restockUnits.put(ProductSize.L, 4);

        restockProduct.run(productId, restockUnits);

        Product restockedProduct = productsRepository.findById(productId);

        assertTrue(restockedProduct.hasTotalStock(22));
        assertTrue(restockedProduct.hasSizeStock(ProductSize.S, 8));
        assertTrue(restockedProduct.hasSizeStock(ProductSize.M, 8));
        assertTrue(restockedProduct.hasSizeStock(ProductSize.L, 6));
    }

    @Test
    void fails_if_product_does_not_exist() {
        HashMap<ProductSize, Integer> restockUnits = new HashMap<>();
        restockUnits.put(ProductSize.S, 5);

        assertThrows(ProductDoesNotExist.class, () -> restockProduct.run(ProductId.createNew(-100), restockUnits));
    }

    @Test
    void fails_if_negative_restock_units_are_provided() {
        ProductId productId = createProductWithPreviousStock();
        HashMap<ProductSize, Integer> restockUnits = new HashMap<>();
        restockUnits.put(ProductSize.S, -1);

        assertThrows(NegativeRestockUnits.class, () -> restockProduct.run(productId, restockUnits));
    }
}
