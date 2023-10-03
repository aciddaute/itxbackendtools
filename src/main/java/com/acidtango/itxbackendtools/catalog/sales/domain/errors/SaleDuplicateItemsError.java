package com.acidtango.itxbackendtools.catalog.sales.domain.errors;

import com.acidtango.itxbackendtools.shared.domain.DomainError;

public class SaleDuplicateItemsError extends DomainError {
    public SaleDuplicateItemsError() {
        super("Different sale items cannot reference the same size of the same product");
    }

}