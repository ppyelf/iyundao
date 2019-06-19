package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Work
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 15:30
 * @Description: 实体 - 工作
 * @Version: V1.0
 */
@Entity
@Table(name = "t_work")
public class Work extends BaseEntity<String> {

    private static final long serialVersionUID = -1230148109284018204L;

    /**
     * 年份
     */
    @Column(name = "YEAR", nullable = false, columnDefinition = "varchar(50) default 0")
    private String year;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 工作类型
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "WORKTYPE")
    private WORK_TYPE workType;

    /**
     * 开始时间
     */
    @Column(name = "STARTTIME", nullable = false, length = 50)
    private String startTime;

    /**
     * 结束时间
     */
    @Column(name = "ENDTIME", nullable = false, length = 50)
    private String endTime;

    /**
     * 说明
     */
    @Column(name = "REMARK", length = 500)
    private String remark;

    /**
     * 工作部门
     */
    @OneToOne(mappedBy = "work", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKSUBJECTID")
    private WorkSubject workSubject;

    /**
     * 工作指标
     */
    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Indicator> workIndicators;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WORK_TYPE getWorkType() {
        return workType;
    }

    public void setWorkType(WORK_TYPE workType) {
        this.workType = workType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public WorkSubject getWorkSubject() {
        return workSubject;
    }

    public void setWorkSubject(WorkSubject workSubject) {
        this.workSubject = workSubject;
    }

    public Set<Indicator> getWorkIndicators() {
        return workIndicators;
    }

    public void setWorkIndicators(Set<Indicator> workIndicators) {
        this.workIndicators = workIndicators;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }

    public enum WORK_TYPE{
        /**
         * 医共体建设
         */
        one(0, "医共体建设"),

        /**
         * 等级医院评审
         */
        two(1, "等级医院评审"),

        /**
         * 最多跑一次
         */
        three(2, "最多跑一次"),

        /**
         * 优质服务专项行动
         */
        four(2, "优质服务专项行动"),

        /**
         * 核心业务指标
         */
        five(2, "核心业务指标");

        private int index;

        private String name;

        WORK_TYPE(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
