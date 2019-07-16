package com.ayundao.base.shiro;

import org.apache.shiro.authc.MergableAuthenticationInfo;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.subject.MutablePrincipalCollection;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: UserAuthenticationInfo
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/10 15:21
 * @Description: shiro - 认证
 * @Version: V1.0
 */
public class UserAuthenticationInfo implements MergableAuthenticationInfo, SaltedAuthenticationInfo{

    protected PrincipalCollection principals;
    protected Object credentials;
    protected ByteSource credentialsSalt;

    public UserAuthenticationInfo() {
    }

    public UserAuthenticationInfo(Object principal, Object credentials, String realmName) {
        this.principals = new SimplePrincipalCollection(principal, realmName);
        this.credentials = credentials;
    }

    public UserAuthenticationInfo(Object principal, Object hashedCredentials, ByteSource credentialsSalt, String realmName) {
        this.principals = new SimplePrincipalCollection(principal, realmName);
        this.credentials = hashedCredentials;
        this.credentialsSalt = credentialsSalt;
    }

    public UserAuthenticationInfo(PrincipalCollection principals, Object credentials) {
        this.principals = new SimplePrincipalCollection(principals);
        this.credentials = credentials;
    }

    public UserAuthenticationInfo(PrincipalCollection principals, Object hashedCredentials, ByteSource credentialsSalt) {
        this.principals = new SimplePrincipalCollection(principals);
        this.credentials = hashedCredentials;
        this.credentialsSalt = credentialsSalt;
    }

    @Override
    public PrincipalCollection getPrincipals() {
        return this.principals;
    }

    public void setPrincipals(PrincipalCollection principals) {
        this.principals = principals;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    @Override
    public ByteSource getCredentialsSalt() {
        return this.credentialsSalt;
    }

    public void setCredentialsSalt(ByteSource salt) {
        this.credentialsSalt = salt;
    }

    @Override
    public void merge(org.apache.shiro.authc.AuthenticationInfo info) {
        if (info != null && info.getPrincipals() != null && !info.getPrincipals().isEmpty()) {
            if (this.principals == null) {
                this.principals = info.getPrincipals();
            } else {
                if (!(this.principals instanceof MutablePrincipalCollection)) {
                    this.principals = new SimplePrincipalCollection(this.principals);
                }

                ((MutablePrincipalCollection)this.principals).addAll(info.getPrincipals());
            }

            if (this.credentialsSalt == null && info instanceof SaltedAuthenticationInfo) {
                this.credentialsSalt = ((SaltedAuthenticationInfo)info).getCredentialsSalt();
            }

            Object thisCredentials = this.getCredentials();
            Object otherCredentials = info.getCredentials();
            if (otherCredentials != null) {
                if (thisCredentials == null) {
                    this.credentials = otherCredentials;
                } else {
                    if (!(thisCredentials instanceof Collection)) {
                        Set newSet = new HashSet();
                        newSet.add(thisCredentials);
                        this.setCredentials(newSet);
                    }

                    Collection credentialCollection = (Collection)this.getCredentials();
                    if (otherCredentials instanceof Collection) {
                        credentialCollection.addAll((Collection)otherCredentials);
                    } else {
                        credentialCollection.add(otherCredentials);
                    }

                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof UserAuthenticationInfo)) {
            return false;
        } else {
            UserAuthenticationInfo that = (UserAuthenticationInfo)o;
            if (this.principals != null) {
                if (!this.principals.equals(that.principals)) {
                    return false;
                }
            } else if (that.principals != null) {
                return false;
            }

            return true;
        }
    }

    @Override
    public int hashCode() {
        return this.principals != null ? this.principals.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.principals.toString();
    }

}
