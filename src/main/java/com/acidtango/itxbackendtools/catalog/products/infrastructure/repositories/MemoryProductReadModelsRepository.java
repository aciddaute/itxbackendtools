package com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories;

import com.acidtango.itxbackendtools.catalog.products.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

public class MemoryProductReadModelsRepository implements ProductReadModelsRepository {

    @Autowired
    ProductsRepository productsRepository;

    @Override
    public List<ProductReadModel> getProducts(ProductsSortingSpecification sortingSpecification) {
        List<Product> allProducts = productsRepository.getProducts();

        return allProducts.stream().map(product -> new ProductReadModel(product.toPrimitives(),
                sortingSpecification.computeCompoundSortingScore(product).getValue())).sorted(Comparator.comparing(ProductReadModel::sortingScore)).toList();
    }
}
