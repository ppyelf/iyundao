package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: UserGroup
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 14:51
 * @Description: 用户组
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_group")
public class UserGroup extends BaseEntity<String> {

    private static final long serialVerisonUID = -129374981273498L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 负责人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private User user;

    /**
     * 父级--用户组
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FATHERID")
    private UserGroup father;

    /**
     * 用户组关系
     */
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserGroupRelation> userGroupRelations;

//    /**
//     * 字段关系
//     */
//    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<FieldRole> fieldRoles;

//    /**
//     * 按钮关系
//     */
//    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserGroup getFather() {
        return father;
    }

    public void setFather(UserGroup father) {
        this.father = father;
    }

    public Set<UserGroupRelation> getUserGroupRelations() {
        return userGroupRelations;
    }

    public void setUserGroupRelations(Set<UserGroupRelation> userGroupRelations) {
        this.userGroupRelations = userGroupRelations;
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
