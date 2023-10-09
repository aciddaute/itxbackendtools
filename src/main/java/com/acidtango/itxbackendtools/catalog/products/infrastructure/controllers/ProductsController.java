package com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers;


import com.acidtango.itxbackendtools.catalog.products.application.CreateProduct;
import com.acidtango.itxbackendtools.catalog.products.application.GetProduct;
import com.acidtango.itxbackendtools.catalog.products.application.ListProducts;
import com.acidtango.itxbackendtools.catalog.products.application.RestockProduct;
import com.acidtango.itxbackendtools.catalog.products.domain.Product;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductReadModel;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductsSortingSpecification;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private CreateProduct createProductUseCase;

    @Autowired
    private RestockProduct restockProductUseCase;

    @Autowired
    private GetProduct getProductUseCase;

    @Autowired
    private ListProducts listProducts;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    CreateProductResponseDto createProduct(@Valid @RequestBody CreateProductRequestDto body) {
        ProductId newProductId = createProductUseCase.run(body.productName());
        return new CreateProductResponseDto(newProductId.getValue());
    }

    @PatchMapping("/{productId}/restock")
    @ResponseStatus(HttpStatus.OK)
    void restockProduct(@PathVariable Integer productId, @Valid @RequestBody RestockProductRequestDto body) {
        ProductId id = ProductId.fromPrimitives(productId);

        restockProductUseCase.run(id, body.newUnits());
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    ProductResponseDto getProduct(@PathVariable Integer productId) {
        ProductId id = ProductId.fromPrimitives(productId);
        Product product = getProductUseCase.run(id);

        return new ProductResponseDto(product.toPrimitives());
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    ListProductsResponseDto getProducts(@RequestParam(required = false) Double saleUnitsWeight,
                                        @RequestParam(required = false) Double stockWeight) {
        ProductsSortingSpecification sortingSpecification = ProductsSortingSpecification.createNew(saleUnitsWeight,
                stockWeight);

        List<ProductReadModel> products = listProducts.run(sortingSpecification);

        return new ListProductsResponseDto(products.stream().map(ProductReadModelResponseDto::new).toList());
    }
}
