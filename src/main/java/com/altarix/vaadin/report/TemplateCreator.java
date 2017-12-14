package com.altarix.vaadin.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class TemplateCreator {
    protected final static String htmlCode = "<!doctype html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "* {\n" +
            "font-family: Times New Roman;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<#assign SimpleDocs = Root.bands.SimpleDocs/>\n" +
            "<table border=\"1\" cellpadding=\"5\" cellspacing=\"0\">\n" +
            "<thead>\n" +
            "<tr>\n" +
            "<td>Author</td>\n" +
            "<td>Author's organization</td>\n" +
            "<td>Template</td>\n" +
            "<td>Title</td>\n" +
            "<td>Text</td>\n" +
            "<td>Signed by</td>\n" +
            "</tr>\n" +
            "</thead>\n" +
            "<tbody>\n" +
            "<#list SimpleDocs as SimpleDoc>\n" +
            "<tr>\n" +
            "<td>${SimpleDoc.fields.creator.firstName}</td>\n" +
            "<td>${SimpleDoc.fields.creator.organization.fullName}</td>\n" +
            "<td>${SimpleDoc.fields.templateName}</td>\n" +
            "<td>${SimpleDoc.fields.description}</td>\n" +
            "<td>${SimpleDoc.fields.orderText}</td>\n" +
            "<td>${SimpleDoc.fields.signedBy}</td>\n" +
            "</tr>\n" +
            "</#list>\n" +
            "</tbody>\n" +
            "</table>\n" +
            "</body>\n" +
            "</html>";

    public static File getTemplate() {
        File file = new File("template.html");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file); // тут путь куда мы сохраняем наш html файл
            writer.println(htmlCode);
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
        return null;
    }
}
