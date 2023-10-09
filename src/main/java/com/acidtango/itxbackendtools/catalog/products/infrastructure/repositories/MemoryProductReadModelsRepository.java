package com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories;

import com.acidtango.itxbackendtools.catalog.products.domain.*;

import java.util.Comparator;
import java.util.List;

public class MemoryProductReadModelsRepository implements ProductReadModelsRepository {

    ProductsRepository productsRepository;

    public MemoryProductReadModelsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public List<ProductReadModel> getProducts(ProductsSortingSpecification sortingSpecification) {
        List<Product> allProducts = productsRepository.getProducts();

        return allProducts.stream().map(product -> new ProductReadModel(product.toPrimitives(),
                sortingSpecification.computeCompoundSortingScore(product).getValue())).sorted(Comparator.comparing(ProductReadModel::sortingScore).reversed()).toList();
    }
}
