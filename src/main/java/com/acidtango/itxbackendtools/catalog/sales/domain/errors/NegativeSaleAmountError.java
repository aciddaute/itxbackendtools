package com.acidtango.itxbackendtools.catalog.sales.domain.errors;

import com.acidtango.itxbackendtools.shared.domain.DomainError;

public class NegativeSaleAmountError extends DomainError {

    public NegativeSaleAmountError(Integer amount) {
        super("Sale amounts cannot be negative: " + amount.toString());
    }
}
