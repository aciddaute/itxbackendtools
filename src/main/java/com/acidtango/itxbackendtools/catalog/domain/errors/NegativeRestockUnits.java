package com.acidtango.itxbackendtools.catalog.domain.errors;

public class NegativeRestockUnits extends RuntimeException {

    public NegativeRestockUnits(Integer units) {
        super("Trying to restock a product with a negative amount of units " + units.toString());
    }

}
