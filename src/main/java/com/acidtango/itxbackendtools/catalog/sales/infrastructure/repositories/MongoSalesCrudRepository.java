package com.acidtango.itxbackendtools.catalog.sales.infrastructure.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoSalesCrudRepository extends MongoRepository<SaleDocument, Integer> {
}
