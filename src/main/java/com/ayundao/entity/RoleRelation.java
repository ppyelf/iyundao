package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: RoleRelation
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 18:38
 * @Description: 用户角色关系
 * @Version: V1.0
 */
@Entity
@Table(name = "t_role_relation")
public class RoleRelation extends BaseEntity<String> {

    private static final long serialVersionUID = -193279412739472L;

    /**
     * 用户ID
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private User user;

    /**
     * 所属角色
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLEID")
    private Role role;

    /**
     * 拥有角色
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERMISSIONID")
    private Permission permission;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
