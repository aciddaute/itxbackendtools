package com.acidtango.itxbackendtools.catalog.products.domain;

import java.util.List;

public interface ProductReadModelsRepository {

    List<ProductReadModel> getProducts(ProductsSortingSpecification sortingSpecification);
}
