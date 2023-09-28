package com.acidtango.itxbackendtools.catalog;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.CreateProductRequestDto;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.CreateProductResponseDto;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.ProductResponseDto;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.RestockProductRequestDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductCreationAndRestockTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Test
    public void creates_and_restocks_a_product() {
        String productName = "Brand new t-shirt";
        HashMap<ProductSize, Integer> newStock = new HashMap<>();
        newStock.put(ProductSize.S, 15);
        newStock.put(ProductSize.M, 20);
        newStock.put(ProductSize.L, 10);

        Response creationResponse =
                given().port(port).contentType("application/json").body(new CreateProductRequestDto(productName)).when().post("/products");

        assertEquals(creationResponse.getStatusCode(), HttpStatus.CREATED.value());

        Integer productId = creationResponse.getBody().as(CreateProductResponseDto.class).productId();

        given().port(port).contentType("application/json").body(new RestockProductRequestDto(newStock)).when().patch(
                "/products/" + productId.toString() + "/restock").then().statusCode(HttpStatus.OK.value());

        ProductResponseDto product =
                given().port(port).when().get("/products/" + productId).getBody().as(ProductResponseDto.class);

        assertEquals(product.stock().smallUnits(), 15);
        assertEquals(product.stock().mediumUnits(), 20);
        assertEquals(product.stock().largeUnits(), 10);
    }

}
