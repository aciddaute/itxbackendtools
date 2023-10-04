package com.acidtango.itxbackendtools.catalog.sales.infrastructure.controller;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductId;
import com.acidtango.itxbackendtools.catalog.sales.application.CreateSale;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleId;
import com.acidtango.itxbackendtools.catalog.sales.domain.SaleItem;
import com.acidtango.itxbackendtools.catalog.sales.infrastructure.controller.dtos.CreateSaleRequestDto;
import com.acidtango.itxbackendtools.catalog.sales.infrastructure.controller.dtos.CreateSaleResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private CreateSale createSaleUseCase;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    CreateSaleResponseDto createSale(@Valid @RequestBody CreateSaleRequestDto body) {
        SaleId newSaleId =
                createSaleUseCase.run(body.saleItems().stream().map(item -> SaleItem.createNew(ProductId.fromPrimitives(item.productId()), item.size(), item.amount())).toList());
        return new CreateSaleResponseDto(newSaleId.getValue());
    }
}
