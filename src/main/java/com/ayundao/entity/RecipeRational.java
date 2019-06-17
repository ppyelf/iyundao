package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @Author: ink-feather
 * @Description: 处方点评
 * @Date: 2019/6/15 10:35
 */
@Entity
@Table(name = "t_Recipe_rational")
public class RecipeRational extends BaseEntity<String> {

    private static final long serialVersionUID = -1162084710974098503L;

    /**
     *  处方id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECIPEID")
    private Recipe recipe;

    /**
     * 是否合理
     */
    @Column(name = "RATIONAL")
    private String rational;

    /**
     * 点评内容
     */
    @Column(name = "REMARK",length = 500)
    private String remark;

    /**
     * 点评人id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REMARKID")
    private User user;

    /**
     * 点评人姓名
     */
    @Column(name = "REMARKNAME")
    private String remarkName;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRational() {
        return rational;
    }

    public void setRational(String rational) {
        this.rational = rational;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }
}
