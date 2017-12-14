package com.altarix.vaadin.ui;

import com.altarix.vaadin.document.Document;
import com.altarix.vaadin.document.IDocumentMapper;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.math.BigInteger;

@Theme("valo")
public class DocumentByIdView extends VerticalLayout implements View {
    Notification notification;
    Button buttonDocument = new Button("Search");
    Button buttonLink = new Button("Get all documents");
    Layout horizontalLayout = new HorizontalLayout();
    TextField textField;
    IDocumentMapper documentMapper;
    Grid<Document> grid = new Grid<>(Document.class);

    @Autowired
    public DocumentByIdView(IDocumentMapper documentMapper) {
        this.documentMapper = documentMapper;

        addTextField();
        addActionButtonDocument();
        addActionButtonLink();
        horizontalLayout.addComponents(buttonLink, textField, buttonDocument, grid);
        addComponent(horizontalLayout);
    }

    protected void addTextField() {
        textField = new TextField("ID: ");
    }

    protected Document addDocumentById(BigInteger id) {
        return documentMapper.getDocumentById(id);
    }
    
    protected void addActionButtonDocument() {
        buttonDocument.addClickListener(clickEvent -> {
            try {
                BigInteger id = new BigInteger(textField.getValue());
                grid.deselectAll();
                Document document = documentMapper.getDocumentById(id);
                if (document != null)
                    grid.setItems(document);
            } catch (ClassCastException|NumberFormatException e) {
                notification = new Notification("Exception: label's type (must be 'BigInteger')!");
                notification.show(getUI().getPage());
            }
        });
    }
    
    protected void addActionButtonLink() {
        buttonLink.addClickListener(clickEvent -> {
            getUI().getNavigator()
                    .navigateTo(DocumentsUI.BROWSE_DOCUMENTS_ALL);
        });
    }
}
