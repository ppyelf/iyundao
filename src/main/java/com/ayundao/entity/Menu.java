package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @ClassName: Menu
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 14:55
 * @Description: 菜单
 * @Version: V1.0
 */
@Entity
@Table(name = "t_menu")
public class Menu extends BaseEntity<String> {

    private static final long serialVersionUID = -12374981274912789L;

    /**
     * 名称
     */
    @NotNull
    @Max(30)
    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    /**
     * 描述
     */
    @Length(max = 500)
    @Column(name = "REMARK", length = 500)
    private String remark;

    /**
     * 是否公开
     */
    @Column(name = "ISPUBLIC", columnDefinition = "boolean default false")
    private boolean isPublic;

    /**
     * 路径
     */
    @Column(name = "URI", length = 10)
    private String uri;


    /**
     * 父级--菜单
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FATHERID")
    private Menu father;

    /**
     * 等级
     */
    @Column(name = "LEVEL", columnDefinition = "tinyint(1) default 0")
    private int level;

    /**
     * 菜单关系
     */
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MenuRelation> menuRelations;

    /**
     * 页面
     */
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Page> pages;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Menu getFather() {
        return father;
    }

    public void setFather(Menu father) {
        this.father = father;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Set<MenuRelation> getMenuRelations() {
        return menuRelations;
    }

    public void setMenuRelations(Set<MenuRelation> menuRelations) {
        this.menuRelations = menuRelations;
    }

    public Set<Page> getPages() {
        return pages;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }
}
