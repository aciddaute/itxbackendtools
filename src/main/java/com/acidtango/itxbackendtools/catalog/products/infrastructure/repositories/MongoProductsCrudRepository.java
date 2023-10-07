package com.acidtango.itxbackendtools.catalog.products.infrastructure.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoProductsCrudRepository extends MongoRepository<ProductDocument, Integer> {
}
