package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: UserRelation
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 14:31
 * @Description: 用户与机构实体
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_relations")
public class UserRelation extends BaseEntity<String> {

    private static final long serialVersionUID = -1293749127349L;

    /**
     * 所属机构
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBJECTID")
    private Subject subject;

    /**
     * 所属部门
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTID")
    private Depart depart;

    /**
     * 所属小组
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUPSID")
    private Groups groups;

    /**
     * 用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private User user;

    /**
     * 菜单关系
     */
    @OneToMany(mappedBy = "userRelation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MenuRelation> menuRelations;

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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<MenuRelation> getMenuRelations() {
        return menuRelations;
    }

    public void setMenuRelations(Set<MenuRelation> menuRelations) {
        this.menuRelations = menuRelations;
    }
}
