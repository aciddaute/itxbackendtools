package com.acidtango.itxbackendtools.catalog.products.domain;

public interface ProductsSortingCriteria {
    ProductsSortingScore computeScore(Product product);
}
