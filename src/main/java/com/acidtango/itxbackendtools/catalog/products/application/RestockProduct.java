package com.acidtango.itxbackendtools.catalog.products.application;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.shared.application.UseCase;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
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
