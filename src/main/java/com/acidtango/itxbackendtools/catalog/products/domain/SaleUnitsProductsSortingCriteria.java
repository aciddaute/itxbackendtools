package com.acidtango.itxbackendtools.catalog.products.domain;

public class SaleUnitsProductsSortingCriteria extends ProductsSortingCriteria {
    
    public SaleUnitsProductsSortingCriteria(Double weight) {
        super(weight);
    }

    @Override
    public ProductsSortingScore computeScore(Product product) {
        return ProductsSortingScore.createNew(this.weight * product.getSaleUnits());
    }
}
