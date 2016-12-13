package no.exam.dolplads.quizImpl;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import no.exam.dolplads.quizApi.dto.CategoryDto;
import no.exam.dolplads.quizApi.dto.ListDto;
import no.exam.dolplads.quizApi.dto.SubCategoryDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

/**
 * Created by dolplads on 12/12/2016.
 */
public class QuizResourceTestBase {
    public CategoryDto categoryDto;
    public SubCategoryDTO subCategory;


    @BeforeClass
    public static void initClass() {
        JBossUtil.waitForJBoss(10);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/quiz/api/quizzes";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Before
    @After
    public void clean() throws Exception {
        int total = Integer.MAX_VALUE;

        while (total > 0) {
            ListDto<?> listDto = given()
                    .queryParam("limit", Integer.MAX_VALUE)
                    .get()
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(ListDto.class);

            listDto.list.stream()
                    //the "NewsDto" get unmarshalled into a map of fields
                    .map(n -> ((Map) n).get("id"))
                    .forEach(id ->
                            given().delete("/" + id)
                                    .then()
                                    .statusCode(204)
                    );

            total = listDto.totalSize - listDto.list.size();
        }

        String baseTemp = RestAssured.basePath;
        RestAssured.basePath = "/quiz/api/categories";


        List<CategoryDto> categoryDtos = Arrays.asList(given().accept(ContentType.JSON).get()
                .then()
                .statusCode(200)
                .extract().as(CategoryDto[].class));

        categoryDtos.stream().forEach(dto ->
                given().pathParam("id", dto.id)
                        .delete("/{id}")
                        .then().statusCode(204));

        get().then().statusCode(200).body("size()", is(0));

        createTestCategory();
        RestAssured.basePath = baseTemp;
    }

    private void createTestCategory() throws Exception {
        categoryDto = new CategoryDto();
        categoryDto.name = "parentCategory";
        String location1 = postCategory(categoryDto);
        categoryDto = given().accept(ContentType.JSON).get(location1).
                then().statusCode(200).extract().as(CategoryDto.class);

        subCategory = new SubCategoryDTO();
        subCategory.name = "subCategory";
        subCategory.parentCategory = categoryDto;
        String subLocation = postSubCategory(subCategory);

        subCategory = given().accept(ContentType.JSON).get(subLocation).
                then().statusCode(200).extract().as(SubCategoryDTO.class);
    }

    private String postCategory(CategoryDto category) {
        return given().contentType(ContentType.JSON)
                .body(category)
                .post()
                .then()
                .statusCode(201)
                .extract().header("location");
    }

    private String postSubCategory(SubCategoryDTO subCategoryDTO) {
        return given().contentType(ContentType.JSON)
                .body(subCategoryDTO)
                .post(subCategoryDTO.parentCategory.id + "/subcategories")
                .then()
                .statusCode(201)
                .extract().header("Location");
    }
}
