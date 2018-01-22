package com.altarix.vaadin.entry;

import org.springframework.data.annotation.Persistent;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

public class Organization {
    private static final int LOGIN_FIELD_LEN = 50;

    @Id
    @Column(name = "ID")
    @Persistent
    protected UUID id;

    @Column(name = "NAME", length = 200, nullable = false)
    protected String name;

    @Column(name = "FULL_NAME", length = 200)
    protected String fullName;

    @Column(name = "OKPO", length = 10)
    protected String okpo;

    @Column(name = "KPP", length = 9)
    protected String kpp;

    @Column(name = "INN", length = 12)
    protected String inn;

    @Column(name = "POSTAL_ADDRESS", length = 300)
    protected String postalAddress;

    @Column(name = "LEGAL_ADDRESS", length = 300)
    protected String legalAddress;

    @Column(name = "PHONE", length = 100)
    protected String phone;

    @Column(name = "FAX", length = 100)
    protected String fax;

    @Column(name = "EMAIL", length = 100)
    protected String email;

    @Column(name = "COMMENT", length = 1000)
    protected String comment;

    @Column(name = "CODE", length = 20)
    protected String code;

    @Column(name = "HAS_ATTACHMENTS")
    protected Boolean hasAttachments = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECRETARY_ID")
    protected User secretary;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getSecretary() {
        return secretary;
    }

    public void setSecretary(User secretary) {
        this.secretary = secretary;
    }

    public Boolean getHasAttachments() {
        return hasAttachments;
    }

    public void setHasAttachments(Boolean hasAttachments) {
        this.hasAttachments = hasAttachments;
    }
}
