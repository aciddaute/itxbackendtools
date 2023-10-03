package com.acidtango.itxbackendtools.catalog.sales.application;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.sales.domain.*;
import com.acidtango.itxbackendtools.shared.application.UseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateSale extends UseCase {

    private final SalesRepository salesRepository;
    private final ProductsRepository productsRepository;

    public CreateSale(SalesRepository salesRepository, ProductsRepository productsRepository) {
        this.salesRepository = salesRepository;
        this.productsRepository = productsRepository;
    }

    public SaleId run(List<SaleItem> saleItems) {
        Integer nextSaleId = salesRepository.getNextId();

        this.ensureSaleCanBeFulfilled(saleItems);

        Sale newSale = Sale.createNew(SaleId.createNew(nextSaleId), saleItems);

        salesRepository.save(newSale);

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
