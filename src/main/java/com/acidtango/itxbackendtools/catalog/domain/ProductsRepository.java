package com.acidtango.itxbackendtools.catalog.domain;

import java.util.List;

public interface ProductsRepository {

    void save(Product product);

    Integer getNextId();

    List<Product> getProducts();

    Product findById(ProductId id);
}
