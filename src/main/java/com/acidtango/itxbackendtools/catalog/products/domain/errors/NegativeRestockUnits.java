package com.acidtango.itxbackendtools.catalog.products.domain.errors;

import com.acidtango.itxbackendtools.shared.domain.DomainError;

public class NegativeRestockUnits extends DomainError {

    public NegativeRestockUnits(Integer units) {
        super("Trying to restock a product with a negative amount of units " + units.toString());
    }

}
