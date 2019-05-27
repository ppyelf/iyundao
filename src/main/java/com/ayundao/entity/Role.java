package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: Role
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 14:48
 * @Description: 角色
 * @Version: V1.0
 */
@Entity
@Table(name = "t_role")
public class Role extends BaseEntity<String> {

    private static final long serialVersionUID = -12734987129347912l;

    /**
     * 名称
     */
    @Column(name = "NAME", length = 30, nullable = false)
    private String name;

    /**
     * 菜单关系
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MenuRelation> menuRelations;

    /**
     * 角色关系
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles;

    /**
     * 备用字段1
     */
    @Column(name = "INFO1")
    private String info1;

    /**
     * 备用字段2
     */
    @Column(name = "INFO2")
    private String info2;

    /**
     * 备用字段3
     */
    @Column(name = "INFO3")
    private String info3;

    /**
     * 备用字段4
     */
    @Column(name = "INFO4")
    private String info4;

    /**
     * 备用字段5
     */
    @Column(name = "INFO5")
    private String info5;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MenuRelation> getMenuRelations() {
        return menuRelations;
    }

    public void setMenuRelations(Set<MenuRelation> menuRelations) {
        this.menuRelations = menuRelations;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

}
