package com.acidtango.itxbackendtools.catalog.products.application;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.shared.application.UseCase;

public class CreateProduct extends UseCase {

    private final ProductsRepository productsRepository;

    public CreateProduct(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public ProductId run(String productName) {
        Integer nextProductId = productsRepository.getNextId();
        Product newProduct = Product.createNew(ProductId.createNew(nextProductId), productName);

        productsRepository.save(newProduct);

        return newProduct.getId();
    }
}
