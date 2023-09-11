package com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers;


import com.acidtango.itxbackendtools.catalog.products.application.CreateProduct;
import com.acidtango.itxbackendtools.catalog.products.application.RestockProduct;
import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.CreateProductRequestDto;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.CreateProductResponseDto;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.RestockProductRequestDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/")
public class ProductsController {

    @Autowired
    private CreateProduct createProductUseCase;

    @Autowired
    private RestockProduct restockProductUseCase;


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
}