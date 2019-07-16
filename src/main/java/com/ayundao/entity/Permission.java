package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Permission
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/10 10:42
 * @Description: 实体 - 权限
 * @Version: V1.0
 */
@Entity
@Table(name = "t_permission")
public class Permission extends BaseEntity<String> {

    private final static long serialVersionUID = 134981984397213749L;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 权限关系
     */
    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RoleRelation> roleRelations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<RoleRelation> getRoleRelations() {
        return roleRelations;
    }

    public void setRoleRelations(Set<RoleRelation> roleRelations) {
        this.roleRelations = roleRelations;
    }
}
