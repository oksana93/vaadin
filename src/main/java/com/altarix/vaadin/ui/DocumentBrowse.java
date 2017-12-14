package com.altarix.vaadin.ui;

import com.altarix.vaadin.aspect.DocumentAnnotation;
import com.altarix.vaadin.document.Document;
import com.altarix.vaadin.document.IDocumentMapper;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.util.List;

@Theme("valo")
public class DocumentBrowse extends VerticalLayout implements View {
    IDocumentMapper documentMapper;
    Grid<Document> grid = new Grid(Document.class);
    Button buttonLink = new Button("Get document by id");

    public DocumentBrowse(IDocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
        addDocuments();
        addActionButtonLink();

        addComponents(buttonLink, grid);
    }

    protected void addDocuments() {
        List<Document> documents = documentMapper.getDocuments();
        if (documents != null) {
            grid.setWidth(50, Unit.PERCENTAGE);
            grid.setItems(documents);
        }
    }

    protected void addActionButtonLink() {
        buttonLink.setWidth(50, Unit.PERCENTAGE);
        buttonLink.addClickListener(clickEvent -> {
            getUI().getNavigator().navigateTo(DocumentsUI.BROWSE_DOCUMENT_BY_ID);
        });
    }
}
