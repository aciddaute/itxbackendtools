package com.acidtango.itxbackendtools.catalog.sales.application;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.sales.domain.*;
import com.acidtango.itxbackendtools.shared.application.UseCase;
import com.acidtango.itxbackendtools.shared.domain.EventBus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateSale extends UseCase {

    private final SalesRepository salesRepository;
    private final ProductsRepository productsRepository;
    private final EventBus eventBus;

    public CreateSale(SalesRepository salesRepository, ProductsRepository productsRepository, EventBus eventBus) {
        this.salesRepository = salesRepository;
        this.productsRepository = productsRepository;
        this.eventBus = eventBus;
    }

    public SaleId run(List<SaleItem> saleItems) {
        Integer nextSaleId = salesRepository.getNextId();

        this.ensureSaleCanBeFulfilled(saleItems);

        Sale newSale = Sale.createNew(SaleId.createNew(nextSaleId), saleItems);

        salesRepository.save(newSale);
        eventBus.publish(newSale.getRegisteredDomainEvents());

        return newSale.getId();
    }

    private void ensureSaleCanBeFulfilled(List<SaleItem> saleItems) {
        for (SaleItem item : saleItems) {
            RequiredStock requiredStock = item.toRequiredStock();
            Product product = productsRepository.findById(requiredStock.productId());

            product.ensureStockAvailability(requiredStock);
        }
    }
}
