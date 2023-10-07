package com.acidtango.itxbackendtools.catalog.products.domain;

public class SaleUnitsProductsSortingCriteria implements ProductsSortingCriteria {

    @Override
    public ProductsSortingScore computeScore(Product product) {
        return ProductsSortingScore.createNew(product.getSaleUnits());
    }
}
