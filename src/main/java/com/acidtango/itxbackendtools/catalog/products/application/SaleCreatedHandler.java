package com.acidtango.itxbackendtools.catalog.products.application;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.sales.domain.events.SaleCreated;
import com.acidtango.itxbackendtools.catalog.sales.domain.primitives.SaleItemPrimitives;
import com.acidtango.itxbackendtools.shared.application.DomainEventHandler;
import org.springframework.stereotype.Component;

@Component
public class SaleCreatedHandler extends DomainEventHandler<SaleCreated> {

    private final ProductsRepository productsRepository;

    public SaleCreatedHandler(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public void handle(SaleCreated saleCreated) {
        for (SaleItemPrimitives saleItem : saleCreated.items) {
            Product product = productsRepository.findById(ProductId.createNew(saleItem.productId()));
            product.adjustStockAfterSale(saleItem.productSize(), saleItem.amount());
            productsRepository.save(product);
        }
    }
}
