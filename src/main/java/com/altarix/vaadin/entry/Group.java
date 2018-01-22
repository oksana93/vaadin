package com.altarix.vaadin.entry;

import org.springframework.data.annotation.Persistent;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.persistence.*;
import javax.validation.Constraint;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class Group {
    private static final int LOGIN_FIELD_LEN = 50;

    @Id
    @Column(name = "ID")
    @Persistent
    protected UUID id;

    @Version
    @Column(name = "VERSION")
    protected Integer version;

    @Column(name = "UPDATE_TS")
    protected Date updateTs;

    @Column(name = "UPDATED_BY", length = LOGIN_FIELD_LEN)
    protected String updatedBy;

    @Column(name = "DELETE_TS")
    protected Date deleteTs;

    @Column(name = "DELETED_BY", length = LOGIN_FIELD_LEN)
    protected String deletedBy;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Group parent;

    @OneToMany(mappedBy = "group")
    private Set<Constraint> constraints;

    @OneToMany(mappedBy = "group")
    private Set<SessionAttribute> sessionAttributes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public Date getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean isDeleted() {
        return deleteTs != null;
    }

    public Date getDeleteTs() {
        return deleteTs;
    }

    public void setDeleteTs(Date deleteTs) {
        this.deleteTs = deleteTs;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(Set<Constraint> constraints) {
        this.constraints = constraints;
    }

    public Set<SessionAttribute> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(Set<SessionAttribute> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                '}';
    }
}
