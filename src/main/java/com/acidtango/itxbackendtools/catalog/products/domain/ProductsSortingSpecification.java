package com.acidtango.itxbackendtools.catalog.products.domain;

import java.util.Optional;

public class ProductsSortingSpecification {

    private final Double saleUnitsCriteriaWeight;

    private final Double stockCriteriaWeight;

    private ProductsSortingSpecification(Double saleUnitsCriteriaWeight, Double stockCriteriaWeight) {
        this.saleUnitsCriteriaWeight = saleUnitsCriteriaWeight;
        this.stockCriteriaWeight = stockCriteriaWeight;
    }

    public static ProductsSortingSpecification createNew(Double saleUnitsCriteriaWeight, Double stockCriteriaWeight) {
        return new ProductsSortingSpecification(Optional.ofNullable(saleUnitsCriteriaWeight).orElse(1d),
                Optional.ofNullable(stockCriteriaWeight).orElse(1d));
    }

    public ProductsSortingScore computeCompoundSortingScore(Product product) {
        SaleUnitsProductsSortingCriteria saleUnitsCriteria =
                new SaleUnitsProductsSortingCriteria(saleUnitsCriteriaWeight);
        StockProductsSortingCriteria stockCriteria = new StockProductsSortingCriteria(stockCriteriaWeight);

        return ProductsSortingScore.zero().add(saleUnitsCriteria.computeScore(product)).add(stockCriteria.computeScore(product));
    }
}
