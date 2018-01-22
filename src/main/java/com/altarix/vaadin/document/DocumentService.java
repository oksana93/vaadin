package com.altarix.vaadin.document;

import com.altarix.vaadin.aspect.DocumentAnnotation;
import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.GroovyDataLoader;
import com.haulmont.yarg.loaders.impl.JsonDataLoader;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.xml.impl.DefaultXmlReader;
import com.haulmont.yarg.util.groovy.DefaultScriptingImpl;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.toList;

@Service
public class DocumentService {

    protected final static RestTemplate restTemplate = new RestTemplate();
    protected final IDocumentMapper documentMapper;
    protected String urlFindExtSimpleDoc = "http://localhost:8080/app-portal/api/find.json?e=tezistest$ExtSimpleDoc-070ee24f-8670-f29f-e8e5-044e5ac47818&s=";
    protected final static String urlCommit = "http://localhost:8080/app-portal/api/commit?s=";
    protected final static String urlLogin = "http://localhost:8080/app-portal/api/login?u=admin&p=admin&l=ru";
    protected final static String urlLogout = "http://localhost:8080/app-portal/api/logout";
    protected final static String urlServiceReport = "http://localhost:8080/app-portal/api/service?s=";
    protected final static HttpHeaders headers;
    protected String s;

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

    @Autowired
    public DocumentService(IDocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    public List<Document> getDocuments() {
        List<Document> documents = documentMapper.getDocuments();
        return documents;
    }

    public Document getDocumentById(BigInteger id) {
        return documentMapper.getDocumentById(id);
    }

    public void insertDocument(Document document) {
        documentMapper.insertDocument(document);
    }

    public void updateDocument(Document document) {
        documentMapper.updateDocument(document);
    }

    public void deleteDocument(BigInteger id) {
        documentMapper.deleteDocument(id);
    }

    /* ------------------- Login/Logout ----------------------- */

    public String login() {
        return restTemplate.getForObject(urlLogin, String.class);
    }

    @DocumentAnnotation
    public void logout(String s) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("session", s);
        restTemplate.exchange(
                urlLogout,
                HttpMethod.POST,
                getEntity(jsonObject),
                JSONObject.class);
    }

    /* ------------------------------------------ */

    public HttpEntity getEntity(Object o) {
        return new HttpEntity<>(o.toString(), headers);
    }

    public void printBudgetItem(JSONObject jsonObject) {
        try {
            System.out.println("id: [" + jsonObject.get("id") + "]" +
                    "\nname: [" + jsonObject.get("name") + "]" +
                    "\ncode: [" + jsonObject.get("code") + "]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* ------------------- BudgetItem ----------------------- */

    public String createBudgetItem(Map<String, String> map) {
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
    public JSONObject createDocument(Map<String, String> map) {
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
            return new JSONObject(responseJsonObject.getBody());
        } finally {
            logout(s);
        }
    }

    /* ------------------- Report ExtSimpleDoc ----------------------- */
    protected String getNewJsonExtSimpleDoc(String s) {
        //1 Create ExtSimpleDoc

        JSONObject jsonDocKindObject = new JSONObject();
        jsonDocKindObject.put("id", "df$DocKind-c40ea551-d399-4a11-b6be-347ca5f27837");

        JSONObject jsonOrganizationObject = new JSONObject();
        jsonDocKindObject.put("id", "df$DocKind-c40ea551-d399-4a11-b6be-347ca5f27837");

        JSONArray jsonCorrespondentArray = new JSONArray();
        JSONObject jsonCorrespondentObject = new JSONObject();
        jsonCorrespondentObject.put("id", "NEW-df$Correspondent");
        jsonCorrespondentObject.put("name", "ABCD-new");
        jsonCorrespondentArray.put(jsonCorrespondentObject);
        jsonCorrespondentObject = new JSONObject();
        jsonCorrespondentObject.put("id", "NEW-df$Correspondent");
        jsonCorrespondentObject.put("name", "BCDE-new");
        jsonCorrespondentArray.put(jsonCorrespondentObject);
        jsonCorrespondentObject = new JSONObject();
        jsonCorrespondentObject.put("id", "NEW-df$Correspondent");
        jsonCorrespondentObject.put("name", "Correspondent-new");
        jsonCorrespondentArray.put(jsonCorrespondentObject);
        JSONObject jsonExtSimpleDoc = new JSONObject();
        jsonExtSimpleDoc.put("id", "NEW-tezistest$ExtSimpleDoc");
        jsonExtSimpleDoc.put("docKind", jsonDocKindObject);
        jsonExtSimpleDoc.put("correspondentList", jsonCorrespondentArray);

        JSONArray jsonCommitArray = new JSONArray();
        jsonCommitArray.put(jsonExtSimpleDoc);
        JSONObject jsonCommitObject = new JSONObject();
        jsonCommitObject.put("commitInstances", jsonCommitArray);
        /* Request */
        s = login();
        try {
            ResponseEntity<String> responseJsonObject =
                    restTemplate.exchange(
                            urlCommit.concat(s),
                            HttpMethod.POST,
                            getEntity(jsonCommitObject),
                            String.class);
            return responseJsonObject.getBody().substring(2, responseJsonObject.getBody().length() - 1);


        } finally {
            logout(s);
        }
    }

    public String reportDocument() {

        JSONObject param0 = new JSONObject();
        param0.put("id", "report$Report-711a6849-8412-6452-8f8b-0558b876a71c");

        String jsonResultStr = getNewJsonExtSimpleDoc(s);
        JSONObject jsonResult = new JSONObject(jsonResultStr);
        JSONObject jsonExtSimpleDoc = new JSONObject();
        jsonExtSimpleDoc.put("id", jsonResult.get("id"));

        JSONObject params = new JSONObject();
        params.put("param0", param0);
        params.put("param1", jsonExtSimpleDoc);
        params.put("param2", "/home/oksana/IdeaProjects/vaadin");
        params.put("param3", "file_report");
        params.put("param0-Type", "com.haulmont.reports.entity.Report");
        params.put("param1-Type", "com.company.tezistest.entity.ExtSimpleDoc");
        params.put("param2-Type", "java.util.String");
        params.put("param3-Type", "java.util.String");

        JSONObject externalJsonObject = new JSONObject();
        externalJsonObject.put("service", "tezistest_DocumentReportService");
        externalJsonObject.put("method", "printReportByExtSimpleDoc");
        externalJsonObject.put("params", params);
        s = login();
        try {
            /* Request */
            ResponseEntity<String> responseJsonObject =
                    restTemplate.exchange(
                            urlServiceReport.concat(s),
                            HttpMethod.POST,
                            getEntity(externalJsonObject),
                            String.class);
            String filePath = responseJsonObject.getBody();
            return "File saved (" + filePath + ")";
        } finally {
            logout(s);
        }
    }

    public String reportExtSimpleDocTest() throws IOException, JSONException {
        String s = login();
        String jsonExtSimpleDocStr = "";
        JSONObject jsonExtSimpleDoc;
        s = login();
        try {
            jsonExtSimpleDocStr = getNewJsonExtSimpleDoc(s);
            jsonExtSimpleDoc = new JSONObject(jsonExtSimpleDocStr);
        } finally {
            logout(s);
        }

        JSONObject jsonReport = new JSONObject();
        jsonReport.put("extSimpleDoc", jsonExtSimpleDoc);
        jsonReport.put("correspondentList", jsonExtSimpleDoc.getJSONArray("correspondentList"));

        Report report = new DefaultXmlReader().parseXml(
                FileUtils.readFileToString(new File("report_extSimpleDoc.xml")));

        Reporting reporting = new Reporting();
        DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory();
        reporting.setFormatterFactory(formatterFactory);
        reporting.setLoaderFactory(new DefaultLoaderFactory()
                .setGroovyDataLoader(new GroovyDataLoader(new DefaultScriptingImpl()))
                .setJsonDataLoader(new JsonDataLoader()));

        ReportOutputDocument reportOutputDocument = reporting.runReport(new RunParams(report).param("param1", jsonReport),
                new FileOutputStream("report_extSimpleDoc.docx"));


        return "success";
    }
    public String reportDownload(String name, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes(); // bytes download
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + ".docx"))); // download by BufferedOutputStream
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили " + name;
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " => файл пустой.";
        }
    }


    private static final String REPORT_NAME = "report_extSimpleDoc.docx";
    private static final String REPORT_DOCX = "report_extSimpleDoc/docx";

    public void getReport(HttpServletResponse response) throws IOException {

        /* Generate report */

        String s = login();
        String jsonExtSimpleDocStr = "";
        JSONObject jsonExtSimpleDoc;
        s = login();
        try {
            jsonExtSimpleDocStr = getNewJsonExtSimpleDoc(s);
            jsonExtSimpleDoc = new JSONObject(jsonExtSimpleDocStr);
        } finally {
            logout(s);
        }

        JSONObject jsonReport = new JSONObject();
        jsonReport.put("extSimpleDoc", jsonExtSimpleDoc);
        jsonReport.put("correspondentList", jsonExtSimpleDoc.getJSONArray("correspondentList"));

        Report report = new DefaultXmlReader().parseXml(
                FileUtils.readFileToString(new File("report_extSimpleDoc.xml")));

        Reporting reporting = new Reporting();
        DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory();
        reporting.setFormatterFactory(formatterFactory);
        reporting.setLoaderFactory(new DefaultLoaderFactory()
                .setGroovyDataLoader(new GroovyDataLoader(new DefaultScriptingImpl()))
                .setJsonDataLoader(new JsonDataLoader()));

        ReportOutputDocument reportOutputDocument = reporting.runReport(new RunParams(report).param("param1", jsonReport));

        byte[] file = reportOutputDocument.getContent();
        String name = reportOutputDocument.getDocumentName();
        InputStream in = new ByteArrayInputStream(file);
        response.setContentType(REPORT_DOCX);
        response.setHeader("Content-Disposition", "attachment; filename=" + name);
        response.setHeader("Content-Length", String.valueOf(file.length));
        FileCopyUtils.copy(in, response.getOutputStream());


//        File file = new File(REPORT_NAME);
//        InputStream in = new FileInputStream(file);
//        response.setContentType(REPORT_DOCX);
//        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
//        response.setHeader("Content-Length", String.valueOf(file.length()));
//        FileCopyUtils.copy(in, response.getOutputStream());
    }


    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }

            map.put(key, value);
        }
        return map;
    }
}
