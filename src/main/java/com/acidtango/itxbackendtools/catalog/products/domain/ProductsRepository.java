package com.acidtango.itxbackendtools.catalog.products.domain;

public interface ProductsRepository {

    void save(Product product);

    Integer getNextId();

    Product findById(ProductId id);
}
