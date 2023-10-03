package com.acidtango.itxbackendtools.catalog.sales.application;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.products.domain.errors.ProductDoesNotExistError;
import com.acidtango.itxbackendtools.catalog.products.domain.errors.ProductOutOfStockError;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.repository.MemoryProductsRepository;
import com.acidtango.itxbackendtools.catalog.sales.domain.Sale;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleId;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleItem;
import com.acidtango.itxbackendtools.catalog.sales.domain.SalesRepository;
import com.acidtango.itxbackendtools.catalog.sales.domain.errors.SaleDuplicateItemsError;
import com.acidtango.itxbackendtools.catalog.sales.infrastructure.repository.MemorySalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CreateSaleTest {

    private SalesRepository salesRepository = new MemorySalesRepository();

    private ProductsRepository productsRepository = new MemoryProductsRepository();

    private CreateSale createSale = new CreateSale(salesRepository, productsRepository);

    @BeforeEach
    void init() {
        salesRepository = new MemorySalesRepository();
        productsRepository = new MemoryProductsRepository();
        createSale = new CreateSale(salesRepository, productsRepository);
    }

    void createProducts() {
        Product firstProduct = Product.createNew(ProductId.fromPrimitives(1), "V-NECH BASIC SHIRT");
        Product secondProduct = Product.createNew(ProductId.fromPrimitives(2), "CONTRASTING FABRIC T-SHIRT");

        firstProduct.restock(Map.of(ProductSize.S, 4, ProductSize.M, 9, ProductSize.L, 0));
        secondProduct.restock(Map.of(ProductSize.S, 35, ProductSize.M, 9, ProductSize.L, 9));

        productsRepository.save(firstProduct);
        productsRepository.save(secondProduct);
    }

    @Test
    void creates_a_single_product_sale() {
        createProducts();

        SaleId saleId = createSale.run(List.of(SaleItem.createNew(ProductId.fromPrimitives(1), ProductSize.S, 1)));
        Sale createdSale = salesRepository.findById(saleId);

        assertEquals(saleId, createdSale.getId());
        assertTrue(createdSale.hasTotalUnits(1));
    }

    @Test
    void creates_a_several_products_sale() {
        createProducts();

        SaleId saleId = createSale.run(List.of(SaleItem.createNew(ProductId.fromPrimitives(1), ProductSize.S, 3),
                SaleItem.createNew(ProductId.fromPrimitives(2), ProductSize.M, 4)));
        Sale createdSale = salesRepository.findById(saleId);

        assertEquals(saleId, createdSale.getId());
        assertTrue(createdSale.hasTotalUnits(7));
    }

    @Test
    void creates_multiple_products_and_product_sizes_sale() {
        createProducts();

        SaleId saleId = createSale.run(List.of(SaleItem.createNew(ProductId.fromPrimitives(1), ProductSize.S, 2),
                SaleItem.createNew(ProductId.fromPrimitives(1), ProductSize.M, 4),
                SaleItem.createNew(ProductId.fromPrimitives(2), ProductSize.S, 5),
                SaleItem.createNew(ProductId.fromPrimitives(2), ProductSize.M, 4),
                SaleItem.createNew(ProductId.fromPrimitives(2), ProductSize.L, 2)));
        Sale createdSale = salesRepository.findById(saleId);

        assertEquals(saleId, createdSale.getId());
        assertTrue(createdSale.hasTotalUnits(17));
    }

    @Test
    void fails_if_product_does_not_exist() {
        assertThrows(ProductDoesNotExistError.class,
                () -> createSale.run(List.of(SaleItem.createNew(ProductId.fromPrimitives(1), ProductSize.S, 1))));
    }

    @Test
    void fails_if_product_is_out_of_stock() {
        createProducts();

        assertThrows(ProductOutOfStockError.class,
                () -> createSale.run(List.of(SaleItem.createNew(ProductId.fromPrimitives(1), ProductSize.L, 1))));
    }

    @Test
    void fails_if_sale_items_contains_duplicate_entries() {
        createProducts();

        assertThrows(SaleDuplicateItemsError.class,
                () -> createSale.run(List.of(SaleItem.createNew(ProductId.fromPrimitives(1), ProductSize.S, 1),
                        SaleItem.createNew(ProductId.fromPrimitives(1), ProductSize.S, 3))));
    }
}
