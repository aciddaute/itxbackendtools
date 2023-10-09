package com.acidtango.itxbackendtools.catalog;

import com.acidtango.itxbackendtools.catalog.products.domain.ProductSize;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.CreateProductRequestDto;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.ListProductsResponseDto;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.ProductReadModelResponseDto;
import com.acidtango.itxbackendtools.catalog.products.infrastructure.controllers.dtos.RestockProductRequestDto;
import com.acidtango.itxbackendtools.catalog.sales.infrastructure.controller.dtos.CreateSaleRequestDto;
import com.acidtango.itxbackendtools.catalog.sales.infrastructure.controller.dtos.SaleItemRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("mongo")
public class InditexBackendToolsCaseStudyTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int port;

    @BeforeEach
    void init() {
        mongoTemplate.dropCollection("sales");
        mongoTemplate.dropCollection("products");

    }

    void createProducts() {
        given().port(port).contentType("application/json").body(new CreateProductRequestDto("V-NECH BASIC SHIRT"))
               .when().post("/products").then().statusCode(HttpStatus.CREATED.value());
        given().port(port).contentType("application/json")
               .body(new CreateProductRequestDto("CONTRASTING FABRIC " + "T-SHIRT")).when().post("/products").then()
               .statusCode(HttpStatus.CREATED.value());
        given().port(port).contentType("application/json").body(new CreateProductRequestDto("RAISED PRINT T-SHIRT"))
               .when().post("/products").then().statusCode(HttpStatus.CREATED.value());
        given().port(port).contentType("application/json").body(new CreateProductRequestDto("PLEATED T-SHIRT")).when()
               .post("/products").then().statusCode(HttpStatus.CREATED.value());
        given().port(port).contentType("application/json")
               .body(new CreateProductRequestDto("CONTRASTING LACE " + "T" + "-SHIRT")).when().post("/products").then()
               .statusCode(HttpStatus.CREATED.value());
        given().port(port).contentType("application/json").body(new CreateProductRequestDto("SLOGAN T-SHIRT")).when()
               .post("/products").then().statusCode(HttpStatus.CREATED.value());
    }

    void restockProducts() {
        given().port(port).contentType("application/json")
               .body(new RestockProductRequestDto(Map.of(ProductSize.S, 104, ProductSize.M, 9))).when()
               .patch("/products/1/restock").then().statusCode(HttpStatus.OK.value());
        given().port(port).contentType("application/json")
               .body(new RestockProductRequestDto(Map.of(ProductSize.S, 85, ProductSize.M, 9, ProductSize.L, 9))).when()
               .patch("/products/2/restock").then().statusCode(HttpStatus.OK.value());
        given().port(port).contentType("application/json")
               .body(new RestockProductRequestDto(Map.of(ProductSize.S, 100, ProductSize.M, 2, ProductSize.L, 20)))
               .when().patch("/products/3/restock").then().statusCode(HttpStatus.OK.value());
        given().port(port).contentType("application/json")
               .body(new RestockProductRequestDto(Map.of(ProductSize.S, 28, ProductSize.M, 30, ProductSize.L, 10)))
               .when().patch("/products/4/restock").then().statusCode(HttpStatus.OK.value());
        given().port(port).contentType("application/json")
               .body(new RestockProductRequestDto(Map.of(ProductSize.S, 650, ProductSize.M, 1))).when()
               .patch("/products/5/restock").then().statusCode(HttpStatus.OK.value());
        given().port(port).contentType("application/json")
               .body(new RestockProductRequestDto(Map.of(ProductSize.S, 29, ProductSize.M, 2, ProductSize.L, 5))).when()
               .patch("/products/6/restock").then().statusCode(HttpStatus.OK.value());
    }

    void adjustSaleUnits() {
        given().port(port).contentType("application/json")
               .body(new CreateSaleRequestDto(List.of(new SaleItemRequestDto(1, ProductSize.S, 100),
                                                      new SaleItemRequestDto(2, ProductSize.S, 50),
                                                      new SaleItemRequestDto(3, ProductSize.S, 80),
                                                      new SaleItemRequestDto(4, ProductSize.S, 3),
                                                      new SaleItemRequestDto(5, ProductSize.S, 650),
                                                      new SaleItemRequestDto(6, ProductSize.S, 20))))
               .when().post("/sales").then().statusCode(HttpStatus.CREATED.value());
    }

    void setupTest() {
        createProducts();
        restockProducts();
        adjustSaleUnits();
    }

    @Test
    public void retrieves_sorted_products_with_default_sorting_criteria_weights() {
        setupTest();

        ListProductsResponseDto response =
                given().port(port).when().get("/products").getBody().as(ListProductsResponseDto.class);
        List<ProductReadModelResponseDto> products = response.products();

        assertEquals(products.size(), 6);

        assertEquals(products.get(0).id(), 5);
        assertEquals(products.get(0).sortingScore(), 651);
        assertEquals(products.get(1).id(), 3);
        assertEquals(products.get(1).sortingScore(), 122);
        assertEquals(products.get(2).id(), 1);
        assertEquals(products.get(2).sortingScore(), 113);
        assertEquals(products.get(3).id(), 2);
        assertEquals(products.get(3).sortingScore(), 103);
        assertEquals(products.get(4).id(), 4);
        assertEquals(products.get(4).sortingScore(), 68);
        assertEquals(products.get(5).id(), 6);
        assertEquals(products.get(5).sortingScore(), 36);
    }

    @Test
    public void retrieves_sorted_products_with_custom_sorting_criteria_weights() {
        setupTest();

        ListProductsResponseDto response =
                given().port(port).when().queryParam("saleUnitsWeight", "0.0").get("/products").getBody()
                       .as(ListProductsResponseDto.class);
        List<ProductReadModelResponseDto> products = response.products();

        assertEquals(products.size(), 6);

        assertEquals(products.get(0).id(), 4);
        assertEquals(products.get(0).sortingScore(), 65);
        assertEquals(products.get(1).id(), 2);
        assertEquals(products.get(1).sortingScore(), 53);
        assertEquals(products.get(2).id(), 3);
        assertEquals(products.get(2).sortingScore(), 42);
        assertEquals(products.get(3).id(), 6);
        assertEquals(products.get(3).sortingScore(), 16);
        assertEquals(products.get(4).id(), 1);
        assertEquals(products.get(4).sortingScore(), 13);
        assertEquals(products.get(5).id(), 5);
        assertEquals(products.get(5).sortingScore(), 1);
    }

}
