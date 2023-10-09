package com.acidtango.itxbackendtools.catalog.products.application;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductReadModel;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductReadModelsRepository;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsSortingSpecification;
import com.acidtango.itxbackendtools.shared.application.UseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProducts extends UseCase {

    private final ProductReadModelsRepository productReadModelsRepository;

    public ListProducts(ProductReadModelsRepository productsRepository) {
        this.productReadModelsRepository = productsRepository;
    }

    public List<ProductReadModel> run(ProductsSortingSpecification sortingSpecification) {
        return this.productReadModelsRepository.getProducts(sortingSpecification);
    }
}
