package com.acidtango.itxbackendtools.catalog.products.application;

import com.acidtango.itxbackendtools.catalog.products.domain.*;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories.MemoryProductReadModelsRepository;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories.MemoryProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListProductsTest {

    private ProductsRepository productsRepository;

    private ProductReadModelsRepository productReadModelsRepository;

    private ListProducts listProducts;

    @BeforeEach
    void init() {
        productsRepository = new MemoryProductsRepository();
        productReadModelsRepository = new MemoryProductReadModelsRepository(productsRepository);
        listProducts = new ListProducts(productReadModelsRepository);
    }

    void setupTestData() {
        Product firstProduct = Product.createNew(ProductId.createNew(1), "white t-shirt");
        Product secondProduct = Product.createNew(ProductId.createNew(2), "black t-shirt");
        Product thirdProduct = Product.createNew(ProductId.createNew(3), "green t-shirt");

        firstProduct.restock(Map.of(ProductSize.S, 4, ProductSize.M, 5, ProductSize.L, 3));
        secondProduct.restock(Map.of(ProductSize.S, 10, ProductSize.M, 13, ProductSize.L, 9));
        thirdProduct.restock(Map.of(ProductSize.S, 3, ProductSize.M, 3, ProductSize.L, 3));

        firstProduct.adjustStockAfterSale(ProductSize.S, 2);
        firstProduct.adjustStockAfterSale(ProductSize.M, 3);
        firstProduct.adjustStockAfterSale(ProductSize.L, 1);

        secondProduct.adjustStockAfterSale(ProductSize.S, 10);
        secondProduct.adjustStockAfterSale(ProductSize.M, 13);
        secondProduct.adjustStockAfterSale(ProductSize.L, 9);

        productsRepository.save(firstProduct);
        productsRepository.save(secondProduct);
        productsRepository.save(thirdProduct);
    }

    @Test
    void retrieves_products_and_sorts_them_by_sorting_score() {
        setupTestData();

        ProductsSortingSpecification balanced = ProductsSortingSpecification.createNew(1d, 1d);
        ProductsSortingSpecification onlySales = ProductsSortingSpecification.createNew(1d, 0d);
        ProductsSortingSpecification onlyStock = ProductsSortingSpecification.createNew(0d, 1d);

        List<ProductReadModel> balancedResult = listProducts.run(balanced);
        List<ProductReadModel> onlySalesResult = listProducts.run(onlySales);
        List<ProductReadModel> onlyStockResult = listProducts.run(onlyStock);

        assertEquals(balancedResult.size(), 3);
        assertEquals(onlySalesResult.size(), 3);
        assertEquals(onlyStockResult.size(), 3);

        assertEquals(balancedResult.get(0).id(), 2);
        assertEquals(balancedResult.get(0).sortingScore(), 32);
        assertEquals(balancedResult.get(1).id(), 1);
        assertEquals(balancedResult.get(1).sortingScore(), 12);
        assertEquals(balancedResult.get(2).id(), 3);
        assertEquals(balancedResult.get(2).sortingScore(), 9);

        assertEquals(onlySalesResult.get(0).id(), 2);
        assertEquals(onlySalesResult.get(0).sortingScore(), 32);
        assertEquals(onlySalesResult.get(1).id(), 1);
        assertEquals(onlySalesResult.get(1).sortingScore(), 6);
        assertEquals(onlySalesResult.get(2).id(), 3);
        assertEquals(onlySalesResult.get(2).sortingScore(), 0);

        assertEquals(onlyStockResult.get(0).id(), 3);
        assertEquals(onlyStockResult.get(0).sortingScore(), 9);
        assertEquals(onlyStockResult.get(1).id(), 1);
        assertEquals(onlyStockResult.get(1).sortingScore(), 6);
        assertEquals(onlyStockResult.get(2).id(), 2);
        assertEquals(onlyStockResult.get(2).sortingScore(), 0);
    }
}
