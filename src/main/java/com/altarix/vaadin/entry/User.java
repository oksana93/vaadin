package com.altarix.vaadin.entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Persistent;
import sun.awt.AppContext;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name = "sec$User")
@Table(name = "SEC_USER")
public class User {
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

    @Column(name = "LOGIN", length = LOGIN_FIELD_LEN, nullable = false)
    protected String login;

    @Column(name = "LOGIN_LC", length = LOGIN_FIELD_LEN, nullable = false)
    protected String loginLowerCase;

    @Column(name = "PASSWORD", length = 255)
    protected String password;

    @Column(name = "NAME", length = 255)
    protected String name;

    @Column(name = "FIRST_NAME", length = 255)
    protected String firstName;

    @Column(name = "LAST_NAME", length = 255)
    protected String lastName;

    @Column(name = "MIDDLE_NAME", length = 255)
    protected String middleName;

    @Column(name = "POSITION_", length = 255)
    protected String position;

    @Column(name = "EMAIL", length = 100)
    protected String email;

    @Column(name = "LANGUAGE_", length = 20)
    protected String language;

    @Column(name = "TIME_ZONE")
    protected String timeZone;

    @Column(name = "TIME_ZONE_AUTO")
    protected Boolean timeZoneAuto;

    @Column(name = "ACTIVE")
    protected Boolean active = true;

    @Column(name = "CHANGE_PASSWORD_AT_LOGON")
    protected Boolean changePasswordAtNextLogon = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    protected Group group;

    @Column(name = "IP_MASK", length = 200)
    protected String ipMask;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUuid() {
        return id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLoginLowerCase() {
        return loginLowerCase;
    }

    public void setLoginLowerCase(String loginLowerCase) {
        this.loginLowerCase = loginLowerCase;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean getTimeZoneAuto() {
        return timeZoneAuto;
    }

    public void setTimeZoneAuto(Boolean timeZoneAuto) {
        this.timeZoneAuto = timeZoneAuto;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getIpMask() {
        return ipMask;
    }

    public void setIpMask(String ipMask) {
        this.ipMask = ipMask;
    }

    public Boolean getChangePasswordAtNextLogon() {
        return changePasswordAtNextLogon;
    }

    public void setChangePasswordAtNextLogon(Boolean changePasswordAtNextLogon) {
        this.changePasswordAtNextLogon = changePasswordAtNextLogon;
    }

    @Transient
    public String getSalt() {
        return id != null ? id.toString() : "";
    }
}
