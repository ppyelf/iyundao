package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: EvaluationIndex
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/21 9:56
 * @Description: 实体 - 考评指标
 * @Version: V1.0
 */
@Entity
@Table(name = "t_evaluation_index")
public class EvaluationIndex extends BaseEntity<String> {

    private static final long serialVersionUID = 13798471932479821L;

    /**
     * 指标名称
     */
    @Column(name = "NAME", length = 100)
    private String name;

    /**
     * 最低分
     */
    @Column(name = "MIN", length = 3, columnDefinition = "double(2,1) default '0.0'")
    private double min;

    /**
     * 最高分
     */
    @Column(name = "MAX", length = 3, columnDefinition = "double(3,1) default '0.0'")
    private double max;

    /**
     * 指标类型
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "TYPE", length = 1)
    private TYPE type;

    /**
     * 考评
     * @return
     */
    @OneToMany(mappedBy = "evaluationIndex", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Evaluation> evaluations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public enum TYPE{
        /**
         * 加分
         */
        bonus("加分指标"),

        /**
         * 减分
         */
        subtract("减分指标"),

        /**
         * 一票否
         */
        one("一票否");

        private String name;

        TYPE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
