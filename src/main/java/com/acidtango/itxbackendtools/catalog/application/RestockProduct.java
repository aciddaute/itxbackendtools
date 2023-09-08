package com.acidtango.itxbackendtools.catalog.application;

import com.acidtango.itxbackendtools.catalog.domain.Product;
import com.acidtango.itxbackendtools.catalog.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.domain.ProductsRepository;
import com.acidtango.itxbackendtools.shared.application.UseCase;

import java.util.HashMap;

public class RestockProduct extends UseCase {

    private final ProductsRepository productsRepository;

    public RestockProduct(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    public void run(ProductId productId, HashMap<ProductSize, Integer> newUnits) {

        Product product = productsRepository.findById(productId);

        product.restock(newUnits);

        productsRepository.save(product);
    }
}
