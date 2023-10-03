package com.acidtango.itxbackendtools.catalog.products.domain.errors;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;
import com.acidtango.itxbackendtools.shared.domain.DomainError;

public class ProductOutOfStockError extends DomainError {
    public ProductOutOfStockError(ProductId id, ProductSize size) {
        super("Product with id " + id.getValue().toString() + " is out of stock for " + size + " size");
    }
}
