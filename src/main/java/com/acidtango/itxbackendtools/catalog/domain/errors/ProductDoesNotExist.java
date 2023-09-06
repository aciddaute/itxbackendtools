package com.acidtango.itxbackendtools.catalog.domain.errors;

import com.acidtango.itxbackendtools.catalog.domain.ProductId;

public class ProductDoesNotExist extends RuntimeException {

    public ProductDoesNotExist(ProductId id) {
        super("Product with id " + id.getValue().toString() + " doesn't exist");
    }

}
