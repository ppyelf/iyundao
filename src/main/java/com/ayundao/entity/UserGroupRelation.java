package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: UserGroupRelation
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 18:20
 * @Description: 用户组关系
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_group_relation")
public class UserGroupRelation extends BaseEntity<String> {

    private static final long serialVersionUID = -213479124371827L;

    /**
     * 负责人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private User user;

    /**
     * 用户组ID
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERGROUPID")
    private UserGroup userGroup;

    /**
     * 菜单关系
     */
    @OneToMany(mappedBy = "userGroupRelation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MenuRelation> menuRelations;

    /**
     * 字段关系
     */
    @OneToMany(mappedBy = "userGroupRelation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FieldRole> fieldRoles;

    /**
     * 按钮关系
     */
    @OneToMany(mappedBy = "userGroupRelation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ButtonRole> buttonRoles;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Set<MenuRelation> getMenuRelations() {
        return menuRelations;
    }

    public void setMenuRelations(Set<MenuRelation> menuRelations) {
        this.menuRelations = menuRelations;
    }

    public Set<FieldRole> getFieldRoles() {
        return fieldRoles;
    }

    public void setFieldRoles(Set<FieldRole> fieldRoles) {
        this.fieldRoles = fieldRoles;
    }

    public Set<ButtonRole> getButtonRoles() {
        return buttonRoles;
    }

    public void setButtonRoles(Set<ButtonRole> buttonRoles) {
        this.buttonRoles = buttonRoles;
    }
}
