package com.altarix.vaadin.ui;

import com.altarix.vaadin.document.IDocumentMapper;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

@Theme("valo")
@SpringUI
public class DocumentsUI extends UI {
    IDocumentMapper documentMapper;
    final static String BROWSE_DOCUMENTS_ALL = "documents";
    final static String BROWSE_DOCUMENT_BY_ID = "documentById";
    Navigator navigator;

    @Autowired
    public DocumentsUI(IDocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        navigator = new Navigator(this,this);
        navigator.addView(BROWSE_DOCUMENTS_ALL, new DocumentBrowse(documentMapper));
        navigator.addView(BROWSE_DOCUMENT_BY_ID, new DocumentByIdView(documentMapper));
        setNavigator(navigator);
        navigator.navigateTo(BROWSE_DOCUMENTS_ALL);
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }

    @WebServlet(urlPatterns = "/document/documents", name = "DocumentsUI", asyncSupported = true)
    @VaadinServletConfiguration(ui = DocumentsUI.class, productionMode = false)
    public class DocumentUI extends SpringVaadinServlet {

    }
}
