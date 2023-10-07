package com.acidtango.itxbackendtools.catalog.products.domain;

import com.acidtango.itxbackendtools.shared.domain.ValueObject;

public class ProductsSortingScore extends ValueObject {

    private final Double score;

    private ProductsSortingScore(Double score) {
        this.score = score;
    }

    public static ProductsSortingScore createNew(Double score) {
        return new ProductsSortingScore(score);
    }

    public static ProductsSortingScore createNew(Integer score) {
        return new ProductsSortingScore(Double.valueOf(score));
    }

    Double getValue() {
        return score;
    }

    ProductsSortingScore applyWeightMultiplier(Double weight) {
        Double result = score * weight;
        return new ProductsSortingScore(result);
    }
}
