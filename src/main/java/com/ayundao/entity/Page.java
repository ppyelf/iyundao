package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: Page
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 15:14
 * @Description: 页面
 * @Version: V1.0
 */
@Entity
@Table(name = "t_page")
public class Page extends BaseEntity<String> {

    private static final long serivalVersionUID = -129034801289823L;

    /**
     * 标题
     */
    @Column(name = "TITLE", length = 50)
    private String title;

    /**
     * 路径
     */
    @Column(name = "URI", length = 10, unique = true)
    private String uri;

    /**
     * 所属菜单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENUID")
    private Menu menu;

    /**
     * 页面名称 == index(无需后缀)
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 父级页面
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FATHERID")
    private Page father;

    /**
     * 排序
     */
    @Max(2)
    @Column(name = "SORT", columnDefinition = "tinyint default 0")
    private int sort;

    /**
     * 字段
     */
    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Field> fields;

    /**
     * 等级
     */
    @Column(name = "LEVEL", columnDefinition = "tinyint default 0")
    private int level;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Page getFather() {
        return father;
    }

    public void setFather(Page father) {
        this.father = father;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Set<Field> getFields() {
        return fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
