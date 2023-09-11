package com.acidtango.itxbackendtools.shared;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductsRepository;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.repository.MemoryProductsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfiguration {
    
    @Bean
    public ProductsRepository productRepository() {
        return new MemoryProductsRepository();
    }
}
