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
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.commons.io.FileUtils;
import org.hibernate.service.spi.InjectService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.toList;

/**
 * Created by moshi on 25.10.2017.
 */

@RestController
@RequestMapping(value = "/document")
public class DocumentRestController {

    protected final DocumentService documentService;

    @Autowired
    public DocumentRestController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @DocumentAnnotation
    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public List<Document> getDocuments() {
        return documentService.getDocuments();
    }

    @DocumentAnnotation
    @RequestMapping(value = "/documents/{id}", method = RequestMethod.GET)
    public Document getDocumentById(@PathVariable("id") BigInteger id) {
        return documentService.getDocumentById(id);
    }

    @DocumentAnnotation
    @RequestMapping(value = "/insertDocument", method = RequestMethod.POST)
    public void insertDocument(@RequestBody Document document) {
        documentService.insertDocument(document);
    }

    @DocumentAnnotation
    @RequestMapping(value = "/updateDocument", method = RequestMethod.PUT)
    public void updateDocument(@RequestBody Document document) {
        documentService.updateDocument(document);
    }

    @DocumentAnnotation
    @RequestMapping(value = "/deleteDocumentById/{id}", method = RequestMethod.DELETE)
    public void updateDocument(@PathVariable("id") BigInteger id) {
        documentService.deleteDocument(id);
    }

    /* ------------------------------------------ */
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
        return documentService.createBudgetItem(map);
    }

    /* ------------------- BudgetItem ----------------------- */

    @DocumentAnnotation
    @RequestMapping(value = "/tezisInsertDocument", method = RequestMethod.POST)
    public JSONObject createDocument(@RequestBody Map<String, String> map) {
        return documentService.createDocument(map);
    }

    /* ------------------- Report ExtSimpleDoc ----------------------- */

    @DocumentAnnotation
    @RequestMapping(value = "/reportDocument", method = RequestMethod.POST)
    public String reportDocument() {
        return documentService.reportDocument();
    }

    @DocumentAnnotation
    @RequestMapping(value = "/reportBiulder", method = RequestMethod.GET)
    public String reportExtSimpleDocTest() throws IOException, JSONException {
        return documentService.reportExtSimpleDocTest();
    }

    @RequestMapping(value = "/reportDownload", method = RequestMethod.POST)
    @ResponseBody
    public String reportDownload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        return documentService.reportDownload(name,file);
    }


    private static final String REPORT_NAME = "report_extSimpleDoc.docx";
    private static final String REPORT_DOCX = "report_extSimpleDoc/docx";

    @RequestMapping(value = "/getReport", method = RequestMethod.POST)
    @ResponseBody
    public void getReport(HttpServletResponse response) throws IOException {
        documentService.getReport(response);
    }


    private Map<String, Object> toMap(JSONObject object) throws JSONException {
        return documentService.toMap(object);
    }


}
