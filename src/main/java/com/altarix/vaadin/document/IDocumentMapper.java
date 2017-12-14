package com.altarix.vaadin.document;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by moshi on 25.10.2017.
 */
@Mapper
public interface IDocumentMapper extends Repository {
    final String getDocuments = "SELECT * FROM Document d";
    final String getDocumentById = "SELECT * FROM Document d WHERE d.id = #{id}";
    final String insertDocument = "INSERT INTO Document (id,name,date) VALUES (nextval('idGenerator'),#{name},#{date})";
    final String updateDocument = "UPDATE Document SET name = #{name}, date = #{date} WHERE id = #{id}";
    final String deleteDocumentById = "DELETE FROM Document WHERE id = #{id}";

    @Select(getDocumentById)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "date", column = "DATE")
    })
    Document getDocumentById(BigInteger id);

    @Select(getDocuments)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "date", column = "DATE")
    })
    List<Document> getDocuments();

    @Insert(insertDocument)
    void insertDocument(Document document);

    @Update(updateDocument)
    void updateDocument(Document document);

    @Delete(deleteDocumentById)
    void deleteDocument(BigInteger id);
}
