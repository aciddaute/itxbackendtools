package com.acidtango.itxbackendtools.catalog.products.domain.errors;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.shared.domain.DomainError;

public class ProductDoesNotExistError extends DomainError {

    public ProductDoesNotExistError(ProductId id) {
        super("Product with id " + id.getValue().toString() + " doesn't exist");
    }

}
