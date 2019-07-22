package com.ayundao.base.shiro;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.AuthorizationInfo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: UserAuthorizationInfo
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/10 10:50
 * @Description: shiro - 用户授权
 * @Version: V1.0
 */
public class UserAuthorizationInfo implements AuthorizationInfo {

    protected Set<String> organ;

    protected Set<String> roles;

    protected Set<String> stringPermissions;

    protected Set<Permission> objectPermissions;

    public UserAuthorizationInfo() {
    }

    public Set<String> getOrgan() {
        return organ;
    }

    public void setOrgan(Set<String> organ) {
        this.organ = organ;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public Set<String> getStringPermissions() {
        return stringPermissions;
    }

    public void setStringPermissions(Set<String> stringPermissions) {
        this.stringPermissions = stringPermissions;
    }

    @Override
    public Set<Permission> getObjectPermissions() {
        return objectPermissions;
    }

    public void setObjectPermissions(Set<Permission> objectPermissions) {
        this.objectPermissions = objectPermissions;
    }

    public void addOrgan(String organ) {
        if (this.organ == null) {
            this.organ = new HashSet<String>();
        }
        this.organ.add(organ);
    }

    public void addOrgans(Collection<String> organs) {
        if (this.organ == null) {
            this.organ = new HashSet<String>();
        }
        this.organ.addAll(organs);
    }

    public void addRole(String role) {
        if (this.roles == null) {
            this.roles = new HashSet<String>();
        }
        this.roles.add(role);
    }

    public void addRoles(Collection<String> roles) {
        if (this.roles == null) {
            this.roles = new HashSet<String>();
        }
        this.roles.addAll(roles);
    }

    public void addStringPermission(String permission) {
        if (this.stringPermissions == null) {
            this.stringPermissions = new HashSet<String>();
        }
        this.stringPermissions.add(permission);
    }

    public void addStringPermissions(Collection<String> permissions) {
        if (this.stringPermissions == null) {
            this.stringPermissions = new HashSet<String>();
        }
        this.stringPermissions.addAll(permissions);
    }

    public void addObjectPermission(Permission permission) {
        if (this.objectPermissions == null) {
            this.objectPermissions = new HashSet<Permission>();
        }
        this.objectPermissions.add(permission);
    }

    public void addObjectPermissions(Collection<Permission> permissions) {
        if (this.objectPermissions == null) {
            this.objectPermissions = new HashSet<Permission>();
        }
        this.objectPermissions.addAll(permissions);
    }
}
