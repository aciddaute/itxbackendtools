package com.acidtango.itxbackendtools.catalog.products.domain;

public class ProductsSortingSpecification {

    private final Double saleUnitsCriteriaWeight;

    private final Double stockCriteriaWeight;

    private ProductsSortingSpecification(Double saleUnitsCriteriaWeight, Double stockCriteriaWeight) {
        this.saleUnitsCriteriaWeight = saleUnitsCriteriaWeight;
        this.stockCriteriaWeight = stockCriteriaWeight;
    }

    public static ProductsSortingSpecification createNew(Double saleUnitsWeight, Double stockCriteriaWeight) {
        return new ProductsSortingSpecification(saleUnitsWeight, stockCriteriaWeight);
    }

    public ProductsSortingScore computeCompoundSortingScore(Product product) {
        SaleUnitsProductsSortingCriteria saleUnitsCriteria =
                new SaleUnitsProductsSortingCriteria(saleUnitsCriteriaWeight);
        StockProductsSortingCriteria stockCriteria = new StockProductsSortingCriteria(stockCriteriaWeight);

        return ProductsSortingScore.zero().add(saleUnitsCriteria.computeScore(product)).add(stockCriteria.computeScore(product));
    }
}
