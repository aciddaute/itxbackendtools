package com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.products.domain.primitives.ProductPrimitives;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("products")
public class ProductDocument {

    @Id
    Integer id;

    String name;

    Map<ProductSize, Integer> stock;

    Integer saleUnits;


    private ProductDocument(Integer id, String name, Map<ProductSize, Integer> stock, Integer saleUnits) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.saleUnits = saleUnits;
    }

    static ProductDocument fromDomain(Product product) {
        ProductPrimitives primitives = product.toPrimitives();
        return new ProductDocument(primitives.id(), primitives.name(), primitives.stock(), primitives.saleUnits());
    }

    public Product toDomain() {
        return Product.fromPrimitives(new ProductPrimitives(id, name, stock, saleUnits));
    }
}
