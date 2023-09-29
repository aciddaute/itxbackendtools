package com.acidtango.itxbackendtools.catalog.sales.domain;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.sales.domain.errors.NegativeSaleAmountError;
import com.acidtango.itxbackendtools.catalog.sales.domain.primitives.SaleItemPrimitives;
import com.acidtango.itxbackendtools.shared.domain.ValueObject;

public class SaleItem extends ValueObject {

    private final ProductId productId;

    private final ProductSize productSize;

    private final Integer amount;


    private SaleItem(ProductId productId, ProductSize productSize, Integer amount) {
        this.productId = productId;
        this.productSize = productSize;
        this.amount = amount;
    }

    public static SaleItem createNew(ProductId productId, ProductSize productSize, Integer amount) {

        if (amount < 0) {
            throw new NegativeSaleAmountError(amount);
        }

        return new SaleItem(productId, productSize, amount);
    }

    public static SaleItem fromPrimitives(SaleItemPrimitives primitives) {
        return new SaleItem(ProductId.fromPrimitives(primitives.productId()), primitives.productSize(),
                primitives.amount());
    }

    public SaleItemPrimitives toPrimitives() {
        return new SaleItemPrimitives(productId.getValue(), productSize, amount);
    }

}
