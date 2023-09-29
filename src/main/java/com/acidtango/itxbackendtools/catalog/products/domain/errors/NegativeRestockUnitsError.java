package com.acidtango.itxbackendtools.catalog.products.domain.errors;

import com.acidtango.itxbackendtools.shared.domain.DomainError;

public class NegativeRestockUnitsError extends DomainError {

    public NegativeRestockUnitsError(Integer units) {
        super("Trying to restock a product with a negative amount of units " + units.toString());
    }

}
