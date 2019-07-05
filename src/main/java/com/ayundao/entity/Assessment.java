package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import com.ayundao.base.utils.TimeUtils;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName: Assessment
 * @project: ayundao
 * @author: king
 * @Date: 2019/7/4 9:27
 * @Description: 实体 - 考核
 * @Version: V1.0
 */
@Entity
@Table(name = "t_assessment")
public class Assessment extends BaseEntity<String> {

    private final static long serialVersionUID = -4812084302184098321L;

    /**
     * 编号
     */
    @Column(name = "NUMBER",nullable = false,length = 50)
    private String number;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 类型
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "TYPE", nullable = false)
    private ASSESSMENT_TYPE type;

    /**
     * 总分
     */
    @Column(name = "TOTAL", columnDefinition = "tinyint default 0", length = 4)
    private int total;

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
     * 描述
     */
    @Column(name = "REMARK", length = 500)
    private String remark;

    /**
     * 考核文件
     */
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AssessmentFile> assessmentFiles;

    /**
     * 考核指标
     */
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AssessmentIndex> assessmentIndices;

    /**
     * 考核范围
     */
    @OneToMany(mappedBy = "assessment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<AssessmentRange> assessmentRanges;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ASSESSMENT_TYPE getType() {
        return type;
    }

    public void setType(ASSESSMENT_TYPE type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public Set<AssessmentFile> getAssessmentFiles() {
        return assessmentFiles;
    }

    public void setAssessmentFiles(Set<AssessmentFile> assessmentFiles) {
        this.assessmentFiles = assessmentFiles;
    }

    public Set<AssessmentIndex> getAssessmentIndices() {
        return assessmentIndices;
    }

    public void setAssessmentIndices(Set<AssessmentIndex> assessmentIndices) {
        this.assessmentIndices = assessmentIndices;
    }

//    public Set<AssessmentRange> getAssessmentRanges() {
//        return assessmentRanges;
//    }
//
//    public void setAssessmentRanges(Set<AssessmentRange> assessmentRanges) {
//        this.assessmentRanges = assessmentRanges;
//    }

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

    public enum ASSESSMENT_TYPE{
        /**
         * 0 -个人考核
         */
        personnel(0,"个人考核"),

        /**
         * 1 -支部考核
         */
        branch(1,"支部考核"),

        /**
         * 2 -行政考核
         */
        standard(2,"行政考核"),

        /**
         * 3 -机构考核
         */
        etc(3,"机构考核");
        private int index;

        private String name;
        private ASSESSMENT_TYPE (int index,String name){
            this.index=index;
            this.name=name;
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
