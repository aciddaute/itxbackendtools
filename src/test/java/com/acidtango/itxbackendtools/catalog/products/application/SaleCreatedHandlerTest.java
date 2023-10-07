package com.acidtango.itxbackendtools.catalog.products.application;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.repository.MemoryProductsRepository;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleId;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleItem;
import com.acidtango.itxbackendtools.catalog.sales.domain.events.SaleCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class SaleCreatedHandlerTest {
    private ProductsRepository productsRepository = new MemoryProductsRepository();

    private SaleCreatedHandler saleCreatedHandler = new SaleCreatedHandler(productsRepository);

    @BeforeEach
    void init() {
        productsRepository = new MemoryProductsRepository();
        saleCreatedHandler = new SaleCreatedHandler(productsRepository);
    }

    ProductId createProduct(String name) {
        Integer nextProductId = productsRepository.getNextId();
        Product newProduct = Product.createNew(ProductId.createNew(nextProductId), name);

        newProduct.restock(Map.of(ProductSize.S, 10, ProductSize.M, 15, ProductSize.L, 7));

        productsRepository.save(newProduct);

        return newProduct.getId();
    }

    @Test
    void adjusts_available_stock_and_sale_units() {
        ProductId firstProductId = createProduct("raised print t-shirt");
        ProductId secondProductId = createProduct("pleated t-shirt");

        SaleCreated firstSaleEvent = new SaleCreated(SaleId.createNew(1), List.of(SaleItem.createNew(firstProductId,
                ProductSize.S, 1), SaleItem.createNew(secondProductId, ProductSize.S, 1)));
        SaleCreated secondSaleEvent = new SaleCreated(SaleId.createNew(2), List.of(SaleItem.createNew(firstProductId,
                ProductSize.M, 1), SaleItem.createNew(secondProductId, ProductSize.L, 2)));

        saleCreatedHandler.handle(firstSaleEvent);

        Product firstProductAfterFirstSale = productsRepository.findById(firstProductId);
        Product secondProductAfterFirstSale = productsRepository.findById(secondProductId);

        saleCreatedHandler.handle(secondSaleEvent);

        Product firstProductAfterSecondSale = productsRepository.findById(firstProductId);
        Product secondProductAfterSecondSale = productsRepository.findById(secondProductId);

        assertTrue(firstProductAfterFirstSale.hasTotalStock(31));
        assertTrue(secondProductAfterFirstSale.hasTotalStock(31));
        assertTrue(firstProductAfterFirstSale.hasSaleUnits(1));
        assertTrue(secondProductAfterFirstSale.hasSaleUnits(1));

        assertTrue(firstProductAfterSecondSale.hasTotalStock(30));
        assertTrue(secondProductAfterSecondSale.hasTotalStock(29));
        assertTrue(firstProductAfterSecondSale.hasSaleUnits(2));
        assertTrue(secondProductAfterSecondSale.hasSaleUnits(3));
    }
}
