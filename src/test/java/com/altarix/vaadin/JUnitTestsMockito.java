package com.altarix.vaadin;

import com.altarix.vaadin.document.Document;
import com.altarix.vaadin.document.DocumentService;
import com.altarix.vaadin.document.IDocumentMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.service.spi.InjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class JUnitTestsMockito {
    @Mock
    private IDocumentMapper documentMapper;

    {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private List<Document> documentList;

    // используем аанотацию @InjectMocks для создания mock объекта
    @InjectMocks
    private DocumentService documentService = new DocumentService(documentMapper);

    Logger log = LogManager.getLogger(JUnitTestsMockito.class);

    private Answer<Document> answerDocumentOne = new Answer<Document>() {
        @Override
        public Document answer(InvocationOnMock invocation) throws Throwable {
            Document document = new Document();
            document.setId(BigInteger.ONE);
            document.setDate(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")
                            .parse("2018-01-19 12:12:13.915856"));
            document.setName("Doc 1");
            return document;
        }
    };

    private Answer<Document> answerDocumentTwo = new Answer<Document>() {
        @Override
        public Document answer(InvocationOnMock invocation) throws Throwable {
            Document document = new Document();
            document.setId(BigInteger.valueOf(2));
            document.setDate(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")
                            .parse("2018-01-19 12:12:28.144582"));
            document.setName("Doc 2");
            return document;
        }
    };

    private Answer<Document> answerDocumentThree = new Answer<Document>() {
        @Override
        public Document answer(InvocationOnMock invocation) throws Throwable {
            Document document = new Document();
            document.setId(BigInteger.valueOf(3));
            document.setDate(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")
                            .parse("2018-01-19 12:12:36.692663"));
            document.setName("Doc 3");
            return document;
        }
    };

    private Answer<Document> answerDocumentFour = new Answer<Document>() {
        @Override
        public Document answer(InvocationOnMock invocation) throws Throwable {
            Document document = new Document();
            document.setId(BigInteger.valueOf(4));
            document.setDate(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")
                            .parse("2018-01-19 12:12:36.720840"));
            document.setName("Doc 4");
            return document;
        }
    };


    @Test
    @Before
    public void testGetDocumentById() { // method's mock
        when(documentService.getDocumentById(BigInteger.ONE)).thenAnswer(answerDocumentOne);
        when(documentService.getDocumentById(BigInteger.valueOf(2))).thenAnswer(answerDocumentTwo);
        when(documentService.getDocumentById(BigInteger.valueOf(3))).thenAnswer(answerDocumentThree);
        when(documentService.getDocumentById(BigInteger.valueOf(4))).thenAnswer(answerDocumentFour);

        log.info(documentService.getDocumentById(BigInteger.valueOf(new Random().nextInt(5))));
    }


    private Answer<List<Document>> answerDocuments = new Answer<List<Document>>() {
        @Override
        public List<Document> answer(InvocationOnMock invocation) throws Throwable {
            List<Document> documentsList = new ArrayList();
            documentsList.add(new Document()
                    .setId(BigInteger.valueOf(anyLong()))
                    .setDate(new Date())
                    .setName(anyString()));
            return documentsList;
        }
    };

    @Test
    @Before
    public void testGetDocuments() {
        when(documentService.getDocuments())
                .thenAnswer(answerDocuments);
    }

    @Test
    @Before
    public void testInsertDocument() {
        doNothing().when(mock(DocumentService.class)).insertDocument(mock(Document.class));
    }
}
