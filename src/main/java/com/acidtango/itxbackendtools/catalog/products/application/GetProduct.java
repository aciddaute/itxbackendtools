package com.acidtango.itxbackendtools.catalog.products.application;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.shared.application.UseCase;
import org.springframework.stereotype.Service;

@Service
public class GetProduct extends UseCase {

    private final ProductsRepository productsRepository;

    public GetProduct(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Product run(ProductId productId) {
        return this.productsRepository.findById(productId);
    }
}
