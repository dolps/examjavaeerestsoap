package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.woact.dolplads.exam2016.backend.entity.Quiz;
import com.woact.dolplads.exam2016.dtos.collection.ListDto;
import com.woact.dolplads.exam2016.dtos.dto.CategoryDto;
import com.woact.dolplads.exam2016.dtos.dto.QuizDto;
import com.woact.dolplads.exam2016.quizApi.rest.QuizResourceTestBase;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by dolplads on 12/12/2016.
 */
public class QuizResourceTestIT extends QuizResourceTestBase {
    @Test
    public void testCreateAndGetQuiz() throws Exception {
        List<String> answers = new ArrayList<>();
        answers.add("1");
        answers.add("2");
        answers.add("correct");
        answers.add("4");
        QuizDto quizDto = new QuizDto("which is correct", answers, 2);
        quizDto.subCategoryId = subCategory.id;

        String location = postQuiz(quizDto);
        given().accept(ContentType.JSON).get(location).then().statusCode(200)
                .body("question", is("which is correct"))
                .body("subCategoryId", is(subCategory.id.intValue()));

    }

    @Test
    public void testGetRandom() throws Exception {
        List<String> answers = new ArrayList<>();
        answers.add("1");
        answers.add("2");
        answers.add("correct");
        answers.add("4");
        QuizDto quizDto = new QuizDto("which is correct", answers, 2);
        quizDto.subCategoryId = subCategory.id;

        String location = postQuiz(quizDto);
        Long id = given().accept(ContentType.JSON).get(location).then().statusCode(200).extract().as(QuizDto.class).id;
        given().accept(ContentType.JSON).get("/random").then().statusCode(200).body("id", is(id.intValue()));
    }

    @Test
    public void testPatchChangeQuestion() throws Exception {
        List<String> answers = getAnswers();

        QuizDto quizDto = new QuizDto("which is correct", answers, 2);
        quizDto.subCategoryId = subCategory.id;

        String location = postQuiz(quizDto);
        Long id = given().accept(ContentType.JSON).get(location).then().statusCode(200).extract().as(QuizDto.class).id;


        patchWithMergeJSon(id, "{\"question\":\"newQuestion\"}", 204);

        given().accept(ContentType.JSON).get(location).
                then().statusCode(200).body("question", is("newQuestion"));

    }

    @Test
    public void testSelfLink() throws Exception {
        int n = createSeveralQuizzes(20);
        int limit = 10;

        ListDto<?> listDto = given()
                .queryParam("limit", limit)
                .get()
                .then()
                .statusCode(200)
                .extract()
                .as(ListDto.class);

        assertEquals(n, (int) listDto.totalSize);
        assertEquals(0, (int) listDto.rangeMin);
        assertEquals(limit - 1, (int) listDto.rangeMax);

        assertNull(listDto._links.previous);
        assertNotNull(listDto._links.next);
        assertNotNull(listDto._links.self);

        ListDto<?> selfDto = given()
                .get(listDto._links.self.href)
                .then()
                .statusCode(200)
                .extract()
                .as(ListDto.class);


        Set<String> first = getQuestions(listDto);
        Set<String> self = getQuestions(selfDto);

        assertContainsTheSame(first, self);
    }

    @Test
    public void testNextLink() throws Exception {
        int n = createSeveralQuizzes(30);
        int limit = 10;

        ListDto<?> listDto = given()
                .queryParam("limit", limit)
                .get()
                .then()
                .statusCode(200)
                .extract()
                .as(ListDto.class);

        assertEquals(n, (int) listDto.totalSize);
        assertNotNull(listDto._links.next.href);

        Set<String> values = getQuestions(listDto);
        String next = listDto._links.next.href;

        int counter = 0;

        while (next != null) {
            counter++;

            int beforeNextSize = values.size();

            listDto = given()
                    .get(next)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(ListDto.class);

            values.addAll(getQuestions(listDto));

            assertEquals(beforeNextSize + limit, values.size());
            assertEquals(counter * limit, (int) listDto.rangeMin);
            assertEquals(listDto.rangeMin + limit - 1, (int) listDto.rangeMax);

            if (listDto._links.next != null) {
                next = listDto._links.next.href;
            } else {
                next = null;
            }
        }

        assertEquals(n, values.size());
    }

    @Test
    public void testPreviousLink() throws Exception {
        int n = createSeveralQuizzes(30);
        int limit = 10;

        ListDto<?> listDto = given()
                .queryParam("limit", limit)
                .get()
                .then()
                .statusCode(200)
                .extract()
                .as(ListDto.class);

        Set<String> first = getQuestions(listDto);

        ListDto<?> nextDto = given()
                .get(listDto._links.next.href)
                .then()
                .statusCode(200)
                .extract()
                .as(ListDto.class);

        Set<String> next = getQuestions(nextDto);

        assertTrue(!first.contains(next.iterator().next()));

        ListDto<?> previousDto = given()
                .get(nextDto._links.previous.href)
                .then()
                .statusCode(200)
                .extract()
                .as(ListDto.class);

        Set<String> previous = getQuestions(previousDto);
        assertContainsTheSame(first, previous);
    }


    @Test
    @SuppressWarnings("unchecked")
    public void testFilter() throws Exception {
        List<String> answers = getAnswers();

        QuizDto quizDto = new QuizDto("which is correct", answers, 2);
        quizDto.subCategoryId = subCategory.id;

        postQuiz(quizDto);

        given()
                .get()
                .then()
                .statusCode(200).body("list.size()", is(1));

        given().queryParam("filter", subCategory.id)
                .get()
                .then()
                .statusCode(200).body("list.size()", is(1));

        given().queryParam("filter", subCategory.id + 1)
                .get()
                .then()
                .statusCode(200).body("list.size()", is(0));
    }

    private Set<String> getQuestions(ListDto<?> selfDto) {

        Set<String> values = new HashSet<>();
        selfDto.list.stream()
                .map(m -> (String) ((Map) m).get("question"))
                .forEach(values::add);

        return values;
    }

    private int createSeveralQuizzes(int n) {
        List<String> answers = getAnswers();

        for (int i = 0; i < n; i++) {
            QuizDto quiz = new QuizDto("which is correct " + i, answers, 2);
            quiz.subCategoryId = subCategory.id;
            postQuiz(quiz);
        }

        return n;
    }


    private String postQuiz(QuizDto quizDto) {
        return given().contentType(ContentType.JSON)
                .body(quizDto)
                .post()
                .then()
                .statusCode(201)
                .extract().header("location");
    }


    private void patchWithMergeJSon(long id, String jsonBody, int statusCode) {
        given().contentType("application/merge-patch+json")
                .body(jsonBody)
                .patch("{id}", id)
                .then()
                .statusCode(statusCode);
    }

    private void assertContainsTheSame(Collection<?> a, Collection<?> b) {
        assertEquals(a.size(), b.size());
        a.forEach(v -> assertTrue(b.contains(v)));
        b.forEach(v -> assertTrue(a.contains(v)));
    }

    public List<String> getAnswers() {
        List<String> answers = new ArrayList<>();
        answers.add("1");
        answers.add("2");
        answers.add("correct");
        answers.add("4");

        return answers;
    }
}