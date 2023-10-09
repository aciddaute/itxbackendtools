package com.acidtango.itxbackendtools.catalog.products.domain;

import com.acidtango.itxbackendtools.shared.domain.ValueObject;

public class ProductsSortingScore extends ValueObject implements Comparable<ProductsSortingScore> {

    private final Double score;

    private ProductsSortingScore(Double score) {
        this.score = score;
    }

    public static ProductsSortingScore createNew(Double score) {
        return new ProductsSortingScore(score);
    }

    public static ProductsSortingScore zero() {
        return new ProductsSortingScore(0d);
    }

    public Double getValue() {
        return score;
    }

    ProductsSortingScore add(ProductsSortingScore other) {
        return new ProductsSortingScore(score + other.score);
    }

    @Override
    public int compareTo(ProductsSortingScore other) {
        return Double.compare(score, other.score);
    }
}
