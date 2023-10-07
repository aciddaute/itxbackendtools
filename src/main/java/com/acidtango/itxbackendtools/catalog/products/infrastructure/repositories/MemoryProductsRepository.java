package com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.products.domain.errors.ProductDoesNotExistError;
import com.acidtango.itxbackendtools.catalog.products.domain.primitives.ProductPrimitives;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Component
@Profile("memory")
public class MemoryProductsRepository implements ProductsRepository {

    public final HashMap<Integer, ProductPrimitives> products = new HashMap<>();

    @Override
    public void save(Product product) {
        ProductPrimitives primitives = product.toPrimitives();
        products.put(primitives.id(), primitives);
    }

    @Override
    public Integer getNextId() {
        return products.size() + 1;
    }

    @Override
    public List<Product> getProducts() {
        return products.values().stream().map(Product::fromPrimitives).toList();
    }

    @Override
    public Product findById(ProductId productId) {
        Optional<ProductPrimitives> primitives = Optional.ofNullable(products.get(productId.getValue()));

        return Product.fromPrimitives(primitives.orElseThrow(() -> new ProductDoesNotExistError(productId)));
    }
}
