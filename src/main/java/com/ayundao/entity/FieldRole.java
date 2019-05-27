package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

/**
 * @ClassName: FieldRole
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 18:15
 * @Description: 字段角色
 * @Version: V1.0
 */
@Entity
@Table(name = "t_field_role")
public class FieldRole extends BaseEntity<String> {

    private static final long serialVersionUID = -1237498712983479L;

    /**
     * 所属字段
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIELDID", nullable = false)
    private Field field;

    /**
     * 所属用户组关系
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERGROUPRELATIONID", nullable = false)
    private UserGroupRelation userGroupRelation;

    /**
     * 所属角色关系
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERROLEID", nullable = false)
    private UserRole userRole;

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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public UserGroupRelation getUserGroupRelation() {
        return userGroupRelation;
    }

    public void setUserGroupRelation(UserGroupRelation userGroupRelation) {
        this.userGroupRelation = userGroupRelation;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
