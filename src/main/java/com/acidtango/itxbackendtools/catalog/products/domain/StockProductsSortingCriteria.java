package com.acidtango.itxbackendtools.catalog.products.domain;

public class StockProductsSortingCriteria extends ProductsSortingCriteria {

    public StockProductsSortingCriteria(Double weight) {
        super(weight);
    }

    @Override
    public ProductsSortingScore computeScore(Product product) {
        return ProductsSortingScore.createNew(this.weight * product.getTotalStock());
    }
}
