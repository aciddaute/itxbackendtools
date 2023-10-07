package com.acidtango.itxbackendtools.catalog.products.domain;

public abstract class ProductsSortingCriteria {

    protected final Double weight;

    public ProductsSortingCriteria(Double weight) {
        this.weight = weight;
    }

    abstract ProductsSortingScore computeScore(Product product);
}
