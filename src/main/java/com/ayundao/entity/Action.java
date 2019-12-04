package com.ayundao.entity;

import com.ayundao.base.BaseComponent;
import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: Action
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/28 11:09
 * @Description: 实体 - 春风行动
 * @Version: V1.0
 */
@Entity
@Table(name = "t_action")
public class Action extends BaseEntity<String> {

    private static final long serialVersionUID = 12374917498197834L;

    /**
     * 机构
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "SUBJECTCODE")),
            @AttributeOverride(name = "name", column = @Column(name = "SUBJECTNAME"))
    })
    private BaseComponent subject;

    /**
     * 小组
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "GROUPCODE")),
            @AttributeOverride(name = "name", column = @Column(name = "GROUPNAME"))
    })
    @Column(name = "GROUPID", length = 50)
    private BaseComponent group;

    /**
     * 用户
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "USERCODE")),
            @AttributeOverride(name = "name", column = @Column(name = "USERNAME"))
    })
    @Column(name = "USERID", nullable = false, length = 50)
    private BaseComponent user;

    /**
     * 金额
     */
    @Column(name = "MONEY", length = 5, columnDefinition = "bigint(5) default 0")
    private long money;

    /**
     * 所属医德医风
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EVALUATIONID", nullable = false)
    private Evaluation evaluation;

    public BaseComponent getSubject() {
        return subject;
    }

    public void setSubject(BaseComponent subject) {
        this.subject = subject;
    }

    public BaseComponent getGroup() {
        return group;
    }

    public void setGroup(BaseComponent group) {
        this.group = group;
    }

    public BaseComponent getUser() {
        return user;
    }

    public void setUser(BaseComponent user) {
        this.user = user;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
