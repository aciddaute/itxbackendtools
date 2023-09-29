package com.acidtango.itxbackendtools;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.repository.MemoryProductsRepository;
import com.acidtango.itxbackendtools.catalog.sales.domain.SalesRepository;
import com.acidtango.itxbackendtools.catalog.sales.infrastructure.repository.MemorySalesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfiguration {

    @Bean
    public ProductsRepository productRepository() {
        return new MemoryProductsRepository();
    }

    @Bean
    public SalesRepository salesRepository() {
        return new MemorySalesRepository();
    }
}
