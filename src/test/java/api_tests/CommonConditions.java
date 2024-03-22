package api_tests;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.*;

import java.util.*;

class CommonConditions {
    protected static final Logger log = Logger.getLogger(APITest.class);
    String[] images = {"I.png", "J.png", "K.png", "L.png", "O.png", "S.png", "T.png", "Z.png"};
    String image;

    @BeforeClass
    public void doBeforeTests() {
        log.info("APITests start");
    }

    @BeforeMethod
    public void doBeforeEachTestMethod() {
        log.info("Test Method  is called");
    }

    protected List<Pair<Integer, Integer>> getCoordinatesCheckSample(String key) {
        Map<String, List<Pair<Integer, Integer>>> map = new HashMap<>();
        List<Pair<Integer, Integer>> listZ = Arrays.asList(new Pair<>(0, 5), new Pair<>(1, 4), new Pair<>(1, 5), new Pair<>(2, 4));
        map.put("Z.png", listZ);
        List<Pair<Integer, Integer>> listK = Arrays.asList(new Pair<>(0, 4), new Pair<>(1, 4), new Pair<>(1, 5), new Pair<>(1, 6), new Pair<>(2, 4));
        map.put("K.png", listK);
        List<Pair<Integer, Integer>> listI = Arrays.asList(new Pair<>(2, 4), new Pair<>(2, 5), new Pair<>(2, 6), new Pair<>(2, 7));
        map.put("I.png", listI);
        List<Pair<Integer, Integer>> listJ = Arrays.asList(new Pair<>(1, 4), new Pair<>(1, 5), new Pair<>(1, 6), new Pair<>(2, 6));
        map.put("J.png", listJ);
        List<Pair<Integer, Integer>> listS = Arrays.asList(new Pair<>(0, 4), new Pair<>(1, 4), new Pair<>(1, 5), new Pair<>(2, 5));
        map.put("S.png", listS);
        List<Pair<Integer, Integer>> listT = Arrays.asList(new Pair<>(0, 5), new Pair<>(1, 5), new Pair<>(1, 6), new Pair<>(2, 5));
        map.put("T.png", listT);
        List<Pair<Integer, Integer>> listL = Arrays.asList(new Pair<>(0, 6), new Pair<>(1, 4), new Pair<>(1, 5), new Pair<>(1, 6));
        map.put("L.png", listL);
        List<Pair<Integer, Integer>> listO = Arrays.asList(new Pair<>(0, 5), new Pair<>(0, 6), new Pair<>(1, 5), new Pair<>(1, 6));
        map.put("O.png", listO);
        return map.get(key);
    }

    protected List<Pair<Integer, Integer>> getTetraminoCoordinates(String bodyResponseTxt, int deltaI, int deltaJ) {
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
                        image = images[k];
                        log.info(i + " " + image + " " + j);
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

    @AfterClass
    public void doAfterTests() {
        log.info("APITests are finished");
    }
}
