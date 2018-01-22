package com.altarix.vaadin.document;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Hashtable;

@RestController
public class LdapRestController {
    private static final String CUBA_WEB_LDAP_URLS = "ldap://172.29.134.240:3268";
    private static final String COM_SUN_JNDI_LDAP_LDAP_CTX_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private static final String CUBA_WEB_LDAP_BASE = "DC=alt,DC=altarix,DC=ru"; //CN=SMR-DC02,OU=Domain Controllers,
    private static final String CUBA_WEB_LDAP_USER = "timesheet";
    private static final String CUBA_WEB_LDAP_PASSWORD = "jahC>ae1ai";
    private static final String SIMPLE = "simple";
    private static final String IGNORE = "ignore";
    private static final String JAVA_NAMING_LDAP_ATTRIBUTES_BINARY = "java.naming.ldap.attributes.binary";
    private static final String OBJECT_SID = "objectSID";
    private static final String FILTER_VALUE_USER = "(&(objectClass=user)(objectClass=person))"; // "(&(objectClass=person)(objectClass=user))";
    private static final String FILTER_VALUE_GROUP = "(objectclass=group)"; // "(&(objectClass=person)(objectClass=user))";
    // group (existing) */

/*
    cuba.web.useActiveDirectory=true
    cuba.web.activeDirectoryAuthClass=com.haulmont.cuba.web.auth.LdapAuthProvider
    cuba.web.ldap.base=DC=alt,DC=altarix,DC=ru
    cuba.web.ldap.user=timesheet
    cuba.web.ldap.password=jahC>ae1ai
*/

    /*ldap*/
    @RequestMapping(value = "/getLdapConnection", method = RequestMethod.GET)
    private static void getLdapConnection() throws NamingException {
        try {
            DirContext ctx = serverBind();
//            try {
//                getGroup(ctx);
//            } catch (NamingException e) {
//                e.printStackTrace();
//                throw e;
//            }
            try {
                getUser(ctx);
            } catch (NamingException e) {
                e.printStackTrace();
                throw e;
            }
        } catch (NamingException e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected static DirContext serverBind() throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, COM_SUN_JNDI_LDAP_LDAP_CTX_FACTORY);
        env.put(Context.PROVIDER_URL, CUBA_WEB_LDAP_URLS);
        env.put(Context.SECURITY_AUTHENTICATION,SIMPLE);
        env.put(Context.SECURITY_PRINCIPAL, CUBA_WEB_LDAP_USER); // dn - specify the username
        env.put(Context.SECURITY_CREDENTIALS, CUBA_WEB_LDAP_PASSWORD); // specify the password
        env.put(Context.REFERRAL, IGNORE);
        env.put(JAVA_NAMING_LDAP_ATTRIBUTES_BINARY, OBJECT_SID);
        DirContext ctx = new InitialDirContext(env);
        return ctx;
    }

    protected static void getGroup(DirContext ctx) throws NamingException {
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration users = ctx.search(CUBA_WEB_LDAP_BASE, FILTER_VALUE_GROUP, controls);
        while (users != null && users.hasMoreElements()) {
            SearchResult result = (SearchResult) users.next();
            System.out.println(result.getNameInNamespace());
        }
    }

    protected static void getUser(DirContext ctx) throws NamingException {
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration users = ctx.search(CUBA_WEB_LDAP_BASE, FILTER_VALUE_USER, controls);
        while (users != null && users.hasMoreElements()) {
            SearchResult result = (SearchResult) users.next();
            System.out.println(result.getObject()+ " ~ " + result.getName());
        }
    }
}
