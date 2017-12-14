package com.altarix.vaadin.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Aspect
@Component
public class AspectDocumentFunction {
    protected BigInteger countFunctionAllDocuments = BigInteger.ZERO;
    protected BigInteger countFunctionDocumentById= BigInteger.ZERO;
    protected BigInteger countFunctionInsertDocument = BigInteger.ZERO;
    protected BigInteger countFunctionUpdateDocument = BigInteger.ZERO;
    protected BigInteger countFunctionDeleteDocument = BigInteger.ZERO;

    protected final String ALL_DOCUMENTS = "getDocuments";
    protected final String DOCUMENT_BY_ID= "getDocumentById";
    protected final String INSERT_DOCUMENT = "insertDocument";
    protected final String UPDATE_FUNCTION = "updateDocument";
    protected final String DELETE_FUNCTION = "deleteDocumentById";

    protected Logger logger = LoggerFactory.getLogger(AspectDocumentFunction.class);

    @AfterReturning(pointcut = "@annotation(DocumentAnnotation)")
    public void doAccessCheck(JoinPoint joinPoint) {
        String functionName = joinPoint.getSignature().getName();
        switch (functionName) {
            case ALL_DOCUMENTS:
                countFunctionAllDocuments = countFunctionAllDocuments.add(BigInteger.TEN);
                logger.info("Document's function '" + functionName + "' (" + countFunctionAllDocuments + ")");
                break;
            case DOCUMENT_BY_ID:
                countFunctionDocumentById = countFunctionDocumentById.add(BigInteger.TEN);
                logger.info("Document's function '" + functionName + "' (" + countFunctionDocumentById + ")");
                break;
            case INSERT_DOCUMENT:
                countFunctionInsertDocument = countFunctionInsertDocument.add(BigInteger.TEN);
                logger.info("Document's function '" + functionName + "' (" + countFunctionInsertDocument + ")");
                break;
            case UPDATE_FUNCTION:
                countFunctionUpdateDocument = countFunctionUpdateDocument.add(BigInteger.TEN);
                logger.info("Document's function '" + functionName + "' (" + countFunctionUpdateDocument + ")");
                break;
            case DELETE_FUNCTION:
                countFunctionDeleteDocument = countFunctionDeleteDocument.add(BigInteger.TEN);
                logger.info("Document's function '" + functionName + "' (" + countFunctionDeleteDocument + ")");
                break;
        }
    }

}
