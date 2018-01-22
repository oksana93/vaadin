package com.altarix.vaadin.document;

import java.math.BigInteger;
import java.util.Date;

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

    public Document setDate(Date date) {
        this.date = date;
        return this;
    }

    public Document(BigInteger id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public Document setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public BigInteger getId() {
        return id;
    }

    public Document setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }


}
