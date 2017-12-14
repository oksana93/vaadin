package com.altarix.vaadin.document;

import com.altarix.vaadin.aspect.DocumentAnnotation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by moshi on 25.10.2017.
 */

@RestController
@RequestMapping(value = "/document")
public class DocumentRestController {
//    protected static Logger logger = LoggerFactory.getLogger(AspectDocumentFunction.class);
//    protected static BigInteger idGenerator = BigInteger.ONE;
//    protected static Timer timer;
//    protected static Map<BigInteger, Date> testMapFoLeakSuspects = new HashMap();

    protected final static String urlCommit = "http://localhost:8080/app-portal/api/commit?s=";
    protected final static String urlLogin = "http://localhost:8080/app-portal/api/login?u=admin&p=admin&l=ru";
    protected final static String urlLogout = "http://localhost:8080/app-portal/api/logout";
    protected final static HttpHeaders headers;

    static {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        TimerTask myTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                Date currentDate = new Date();
//                testMapFoLeakSuspects.put(idGenerator, currentDate);
//                idGenerator = idGenerator.add(BigInteger.ONE);
//                logger.info("Map: '" + idGenerator + " - " + currentDate + "'");
//            }
//        };
//        new Timer().schedule(myTimerTask,1000,1);
    }

    protected final IDocumentMapper documentMapper;
    protected final static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public DocumentRestController(IDocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    @DocumentAnnotation
    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public List<Document> getDocuments() {
        List<Document> documents = documentMapper.getDocuments();
        return documents;
    }

    @DocumentAnnotation
    @RequestMapping(value = "/documents/{id}", method = RequestMethod.GET)
    public Document getDocumentById(@PathVariable("id") BigInteger id) {
        return documentMapper.getDocumentById(id);
    }

    @DocumentAnnotation
    @RequestMapping(value = "/insertDocument", method = RequestMethod.POST)
    public void insertDocument(@RequestBody Document document) {
        documentMapper.insertDocument(document);
    }

    @DocumentAnnotation
    @RequestMapping(value = "/updateDocument", method = RequestMethod.PUT)
    public void updateDocument(@RequestBody Document document) {
        documentMapper.updateDocument(document);
    }

    @DocumentAnnotation
    @RequestMapping(value = "/deleteDocumentById/{id}", method = RequestMethod.DELETE)
    public void updateDocument(@PathVariable("id") BigInteger id) {
        documentMapper.deleteDocument(id);
    }

    /* ------------------- Login/Logout ----------------------- */

    @DocumentAnnotation
    private String login() {
        return restTemplate.getForObject(urlLogin, String.class);
    }

    @DocumentAnnotation
    private void logout(String s) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("session", s);
        restTemplate.exchange(
                urlLogout,
                HttpMethod.POST,
                getEntity(jsonObject),
                JSONObject.class);
    }

    /* ------------------------------------------ */

    private HttpEntity getEntity(Object o) {
        return new HttpEntity<>(o.toString(), headers);
    }


    private void printBudgetItem(JSONObject jsonObject) {
        try {
            System.out.println("id: [" + jsonObject.get("id") + "]" +
                    "\nname: [" + jsonObject.get("name") + "]" +
                    "\ncode: [" + jsonObject.get("code") + "]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* ------------------- BudgetItem ----------------------- */

    @DocumentAnnotation
    @RequestMapping(value = "/createBudgetItem", method = RequestMethod.POST)
    public String createBudgetItem(@RequestBody Map<String, String> map) {
        String s = login();
        try {

            JSONObject budgetItemJsonObject = new JSONObject();
            budgetItemJsonObject.put("id", "NEW-tesisbp$BudgetItem");
            budgetItemJsonObject.put("name", map.get("name"));
            budgetItemJsonObject.put("code", "CO");

            JSONArray budgetItemJsonArray = new JSONArray();
            budgetItemJsonArray.put(budgetItemJsonObject);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("commitInstances", budgetItemJsonArray);


            ResponseEntity<String> responseJsonObject =
                    restTemplate.exchange(
                            urlCommit.concat(s),
                            HttpMethod.POST,
                            getEntity(jsonObject),
                            String.class);
            return responseJsonObject.getBody();

        } finally {
            logout(s);
        }
    }

    /* ------------------- BudgetItem ----------------------- */

    @DocumentAnnotation
    @RequestMapping(value = "/tezisInsertDocument", method = RequestMethod.POST)
    public String createDocument(@RequestBody Map<String, String> map) {
        String s = login();
        try {
            /* Создание DocKind */
            JSONObject jsonDocKindObject = new JSONObject();
            jsonDocKindObject.put("id", "df$DocKind-" + map.get("docKind"));
            /* Создание Doc */
            JSONObject jsonDocObject = new JSONObject();
            jsonDocObject.put("id", "NEW-tezistest$ExtSimpleDoc");
            jsonDocObject.put("docKind", jsonDocKindObject);

            /* JSONArray */
            JSONArray budgetItemJsonArray = new JSONArray();
            budgetItemJsonArray.put(jsonDocObject);

            /* JSONObject */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("commitInstances", budgetItemJsonArray);

            /* Request */
            ResponseEntity<String> responseJsonObject =
                    restTemplate.exchange(
                            urlCommit.concat(s),
                            HttpMethod.POST,
                            getEntity(jsonObject),
                            String.class);
            return responseJsonObject.getBody();
        } finally {
            logout(s);
        }
    }

}
