package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

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

    private static final long serialVersionUID = - 12734987129347912l;

    /**
     * 名称
     */
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    /**
     * 等级
     */
    @Column(name = "LEVEL", length = 1, nullable = false, columnDefinition = "tinyint(1) default 0")
    private int level;

//    /**
//     * 菜单关系
//     */
//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<MenuRelation> menuRelations;

    /**
     * 角色关系
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles;

//    /**
//     * 字段关系
//     */
//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<FieldRole> fieldRoles;
//
//    /**
//     * 按钮关系
//     */
//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<ButtonRole> buttonRoles;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

//    public Set<MenuRelation> getMenuRelations() {
//        return menuRelations;
//    }

//    public void setMenuRelations(Set<MenuRelation> menuRelations) {
//        this.menuRelations = menuRelations;
//    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

//    public Set<FieldRole> getFieldRoles() {
//        return fieldRoles;
//    }
//
//    public void setFieldRoles(Set<FieldRole> fieldRoles) {
//        this.fieldRoles = fieldRoles;
//    }
//
//    public Set<ButtonRole> getButtonRoles() {
//        return buttonRoles;
//    }
//
//    public void setButtonRoles(Set<ButtonRole> buttonRoles) {
//        this.buttonRoles = buttonRoles;
//    }
}
