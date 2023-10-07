package com.acidtango.itxbackendtools.catalog.sales.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoSalesCrudRepository extends MongoRepository<SaleDocument, Integer> {
}
