package api_tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

import javafx.util.Pair;

public class APITest {
    private static final Logger log = Logger.getLogger(APITest.class);
    String[] images = {"I.png", "J.png", "K.png", "L.png", "O.png", "S.png", "T.png", "Z.png"};

    @DataProvider
    public Object[][] dataProviderMethod() {
        return new Object[][]{
                {"start"},
                {"save"},
                {"restart"},
                {"0"},
                {"1"},
                {"2"},
                {"3"},
                {"4"}
        };
    }

    @BeforeTest
    public void doBeforeTests() {
        log.info("APITests start");
    }

    @BeforeMethod
    public void doBeforeEachTestMethod() {
        log.info("Test Method  is called");
    }

    @Test(description = "checks if client requests receive successful responses from the server", dataProvider = "dataProviderMethod")
    public void doRequestsGetSuccessfulResponses(String data) {
        log.info("testing for 200 OK response  request-  /" + data);
        RestAssured.when().get("http://localhost:9090/" + data).then().assertThat().statusCode(200);
    }

    @Test(description = "checks if the Game is ON should appear after 'start' request is sent")
    public void shouldGameIsONAppear() {
        log.info("shouldGameIsONAppear Test start");
        Response response =
                given().baseUri("http://localhost:9090/")
                        .when()
                        .get("start")
                        .then()
                        .extract()
                        .response();
        String bodyTxt = response.getBody().asString();
        Document document = Jsoup.parse(bodyTxt);
        Element contentElement = document.getElementById("header");
        log.info(contentElement.text());
        Assert.assertTrue(contentElement.text().contains("Game is ON"));
    }

    @Test(description = "checks if Player name should  appear after 'start' request is sent")
    public void shouldPlayerNameAppear() {
        log.info("shouldPlayerNameAppear Test start");
        boolean isPLayerNamePresent = false;
        List<String> list = new ArrayList<>();
        list.add("Oswaldo");
        list.add("Tommy");
        list.add("Dunny");
        list.add("Bonny");
        list.add("Ira");
        list.add("Wolfy");
        Response response =
                given().baseUri("http://localhost:9090/")
                        .when()
                        .get("start")
                        .then()
                        .extract()
                        .response();
        String bodyTxt = response.getBody().asString();
        Document document = Jsoup.parse(bodyTxt);
        Element contentElement = document.getElementById("content");
        for (String s : list) {
            if (contentElement.text().contains(s)) {
                log.info(contentElement.text());
                isPLayerNamePresent = true;
            }
        }
        Assert.assertTrue(isPLayerNamePresent);
    }

    @Test(description = "checks if the new tetramino image should appear after 'start' request is sent")
    public void shouldTetraminoImageAppear() {
        log.info("shouldTetraminoImageAppear Test start");
        boolean isTetraminoImagePresent = false;
        String[] images = {"I.png", "J.png", "K.png", "L.png", "O.png", "S.png", "T.png", "Z.png"};
        Response response =
                given().baseUri("http://localhost:9090/")
                        .when()
                        .get("start")
                        .then()
                        .extract()
                        .response();
        String bodyTxt = response.getBody().asString();
        Document document = Jsoup.parse(bodyTxt);
        Element tableElement = document.select("table").get(1);
        Elements rows = tableElement.select("tr");// разбиваем нашу таблицу на строки по тегу
        //по номеру индекса получает строку
        for (Element row : rows) {
            Elements cols = row.select("td");// разбиваем полученную строку по тегу  на столбы
            for (int j = 0; j < 12; j++) {
                for (int k = 0; k < 8; k++) {
                    if (cols.get(j).toString().contains(images[k])) {
                        log.info(cols.get(j));
                        isTetraminoImagePresent = true;
                    }
                }
            }
        }
        Assert.assertTrue(isTetraminoImagePresent);
    }

    @Test(description = "checks if the tetramino image should move 1 step down after '0' request is sent")
    public void shouldTetraminoImageMoveDown() {
        log.info("shouldTetraminoImageMoveDown Test start");
        Response responseToStart =
                given().baseUri("http://localhost:9090/")
                        .when()
                        .get("start")
                        .then()
                        .extract()
                        .response();
        String bodyResponseToStartTxt = responseToStart.getBody().asString();
        String id = responseToStart.sessionId();
        Response responseToDown =
                given().sessionId(id)
                        .baseUri("http://localhost:9090/")
                        .when()
                        .get("0")
                        .then()
                        .extract()
                        .response();
        String bodyResponseToDownTxt = responseToDown.getBody().asString();
        Assert.assertEquals(getTetraminoCoordinates(bodyResponseToStartTxt, 1, 0),getTetraminoCoordinates(bodyResponseToDownTxt, 0, 0));
    }

    @Test(description = "checks if the tetramino image should move 1 position left after '2' request is sent")
    public void shouldTetraminoImageMoveLeft() {
        log.info("shouldTetraminoImageMoveLeft Test start");
        Response responseToStart =
                given().baseUri("http://localhost:9090/")
                        .when()
                        .get("start")
                        .then()
                        .extract()
                        .response();
        String bodyResponseToStartTxt = responseToStart.getBody().asString();
        String id = responseToStart.sessionId();
        Response responseToDown =
                given().sessionId(id)
                        .baseUri("http://localhost:9090/")
                        .when()
                        .get("2")
                        .then()
                        .extract()
                        .response();
        String bodyResponseToDownTxt = responseToDown.getBody().asString();
        Assert.assertEquals(getTetraminoCoordinates(bodyResponseToStartTxt, 0, -1),getTetraminoCoordinates(bodyResponseToDownTxt, 0, 0));
    }

    @Test(description = "checks if the tetramino image should move 1 position right after '3' request is sent")
    public void shouldTetraminoImageMoveRight() {
        log.info("shouldTetraminoImageMoveRight Test start");
        Response responseToStart =
                given().baseUri("http://localhost:9090/")
                        .when()
                        .get("start")
                        .then()
                        .extract()
                        .response();
        String bodyResponseToStartTxt = responseToStart.getBody().asString();
        String id = responseToStart.sessionId();
        Response responseToDown =
                given().sessionId(id)
                        .baseUri("http://localhost:9090/")
                        .when()
                        .get("3")
                        .then()
                        .extract()
                        .response();
        String bodyResponseToDownTxt = responseToDown.getBody().asString();
        Assert.assertEquals(getTetraminoCoordinates(bodyResponseToStartTxt, 0, 1),getTetraminoCoordinates(bodyResponseToDownTxt, 0, 0));
    }

    private List<Pair<Integer, Integer>> getTetraminoCoordinates(String bodyResponseTxt, int deltaI, int deltaJ) {
        List<Pair<Integer, Integer>> tetraminoCoordinates = new ArrayList<>();
        Document documentResponseToDown = Jsoup.parse(bodyResponseTxt);
        Element tableElementRTD = documentResponseToDown.select("table").get(1);
        Elements rowsRTD = tableElementRTD.select("tr");// разбиваем нашу таблицу на строки по тегу
        //по номеру индекса получает строку
        for (int i = 0; i < rowsRTD.size(); i++) {
            Elements cols = rowsRTD.get(i).select("td");// разбиваем полученную строку по тегу  на столбы
            for (int j = 0; j < 12; j++) {
                for (int k = 0; k < 8; k++) {
                    if (cols.get(j).toString().contains(images[k])) {
                        log.info(i + " " + j);
                        tetraminoCoordinates.add(new Pair<>(i + deltaI, j + deltaJ));
                    }
                }
            }
        }
        return tetraminoCoordinates;
    }

    @AfterMethod
    public void doAfterEachTestMethod() {
        log.info("Test Method  is finished");
    }

    @AfterTest
    public void doAfterTests() {
        log.info("APITests are finished");
    }
}
