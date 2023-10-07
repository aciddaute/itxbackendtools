package com.acidtango.itxbackendtools.catalog.products.domain;

public class StockProductsSortingCriteria implements ProductsSortingCriteria {

    @Override
    public ProductsSortingScore computeScore(Product product) {
        return ProductsSortingScore.createNew(product.getTotalStock());
    }
}
