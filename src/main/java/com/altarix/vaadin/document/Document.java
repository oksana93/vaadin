package com.altarix.vaadin.document;

import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.sql.Date;

/**
 * Created by moshi on 25.10.2017.
 */
public class Document {

    private BigInteger id;

    private String name;

    private Date date;

    public Document() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Document(BigInteger id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
