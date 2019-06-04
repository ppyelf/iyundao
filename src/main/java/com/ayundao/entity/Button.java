package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: Button
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/16 15:33
 * @Description: 按钮
 * @Version: V1.0
 */
@Entity
@Table(name = "t_button")
public class Button extends BaseEntity<String> {

    private static final long serialVersionUID = -1234798213489L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 所属字段
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIELDID", nullable = false)
    private Field field;

    /**
     * 执行路径
     */
    @Column(name = "URI", length = 10)
    private String uri;

    /**
     * 排序
     */
    @Max(2)
    @Column(name = "SORT", columnDefinition = "tinyint default 0")
    private int sort;

    /**
     * 等级
     */
    @Column(name = "LEVEL", columnDefinition = "tinyint default 0")
    private int level;

    /**
     * 按钮关系
     */
    @OneToMany(mappedBy = "button", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Set<ButtonRole> getButtonRoles() {
        return buttonRoles;
    }

    public void setButtonRoles(Set<ButtonRole> buttonRoles) {
        this.buttonRoles = buttonRoles;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
