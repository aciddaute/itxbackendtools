package com.acidtango.itxbackendtools.catalog.sales.domain.errors;

import com.acidtango.itxbackendtools.catalog.sales.domain.SaleId;
import com.acidtango.itxbackendtools.shared.domain.DomainError;

public class SaleDoesNotExistError extends DomainError {

    public SaleDoesNotExistError(SaleId id) {
        super("Sale with id " + id.getValue().toString() + " doesn't exist");
    }

}
