package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: ExamineText
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/2 10:34
 * @Description: 审批文本
 * @Version: V1.0
 */
@Entity
@Table(name = "t_examine_text")
public class ExamineText extends BaseEntity<String> {

    private static final long serialVersionUID = -12372183917298371L;

    /**
     * 所属审核
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAMINEID", nullable = false)
    private Examine examine;

    /**
     * 事由
     */
    @Column(name = "CAUSE")
    private String cause;

    /**
     * 详情
     */
    @Column(name = "DETAIL", length = 1000)
    private String detail;

    public Examine getExamine() {
        return examine;
    }

    public void setExamine(Examine examine) {
        this.examine = examine;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
