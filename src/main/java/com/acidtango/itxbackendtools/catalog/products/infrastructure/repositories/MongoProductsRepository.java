package com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories;

import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.products.domain.errors.ProductDoesNotExistError;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("mongo")
public class MongoProductsRepository implements ProductsRepository {

    private final MongoProductsCrudRepository crudRepository;

    public MongoProductsRepository(MongoProductsCrudRepository productsRepository) {
        this.crudRepository = productsRepository;
    }

    @Override
    public void save(Product product) {
        crudRepository.save(ProductDocument.fromDomain(product));
    }

    @Override
    public Integer getNextId() {
        return Math.toIntExact(crudRepository.count()) + 1;
    }

    @Override
    public Product findById(ProductId id) {
        Optional<ProductDocument> document = crudRepository.findById(id.getValue());

        if (document.isPresent()) {
            return document.get().toDomain();
        }

        throw new ProductDoesNotExistError(id);
    }
}
