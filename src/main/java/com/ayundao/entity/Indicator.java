package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Indicators
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 16:40
 * @Description: 实体 - 工作指标
 * @Version: V1.0
 */
@Entity
@Table(name = "t_indicator")
public class Indicator extends BaseEntity<String> {

    private final static long serialVersionUID = -103284840912438109L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 指示情况
     */
    @Column(name = "SITUATION", length = 50)
    private String situation;

    /**
     * 父级
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FATHERID")
    private Indicator father;

    /**
     * 所属工作
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKID")
    private Work work;

    /**
     * 指标详情
     */
    @OneToMany(mappedBy = "indicator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<IndicatorInfo> indicatorInfos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Indicator getFather() {
        return father;
    }

    public void setFather(Indicator father) {
        this.father = father;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Set<IndicatorInfo> getIndicatorInfos() {
        return indicatorInfos;
    }

    public void setIndicatorInfos(Set<IndicatorInfo> indicatorInfos) {
        this.indicatorInfos = indicatorInfos;
    }
}
