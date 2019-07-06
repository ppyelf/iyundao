package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Examine
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/22 10:26
 * @Description: 实体 - 审核
 * @Version: V1.0
 */
@Entity
@Table(name = "t_examine")
public class Examine extends BaseEntity<String> {

    private final static long serialVersionUID = -1808213094809128304L;

    /**
     * 开始时间
     */
    @Column(name = "STARTTIME", length = 50)
    private String startTime;

    /**
     * 结束时间
     */
    @Column(name = "ENDTIME", length = 50)
    private String endTime;

    /**
     * 请示/请假类型
     *      0-个人原因,1-工作原因,2-年假,3-事假,4-病假,5-调休,6-产假,7-陪产假,8-婚假,9-例假,10-丧假,11-哺乳假
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "REASONTYPE", length = 1)
    private REASON reasonType;

    /**
     * 审批类型:
     *      0-请假审批,1-请示批复
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "EXAMINETYPE", length = 1, nullable = false)
    private EXAMINE_TYPE type;

    /**
     * 审核流程
     */
    @OneToMany(mappedBy = "examine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamineProcess> examineProcesses;

    /**
     * 审核图片
     */
    @OneToMany(mappedBy = "examine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamineImage> examineImages;

    /**
     * 审核附件
     */
    @OneToMany(mappedBy = "examine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamineFile> examineFiles;

    /**
     * 审批文本
     */
    @OneToOne(mappedBy = "examine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ExamineText examineText;

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

    public EXAMINE_TYPE getType() {
        return type;
    }

    public void setType(EXAMINE_TYPE type) {
        this.type = type;
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

    public REASON getReasonType() {
        return reasonType;
    }

    public void setReasonType(REASON reasonType) {
        this.reasonType = reasonType;
    }

    public Set<ExamineProcess> getExamineProcesses() {
        return examineProcesses;
    }

    public void setExamineProcesses(Set<ExamineProcess> examineProcesses) {
        this.examineProcesses = examineProcesses;
    }

    public Set<ExamineImage> getExamineImages() {
        return examineImages;
    }

    public void setExamineImages(Set<ExamineImage> examineImages) {
        this.examineImages = examineImages;
    }

    public Set<ExamineFile> getExamineFiles() {
        return examineFiles;
    }

    public void setExamineFiles(Set<ExamineFile> examineFiles) {
        this.examineFiles = examineFiles;
    }

    public ExamineText getExamineText() {
        return examineText;
    }

    public void setExamineText(ExamineText examineText) {
        this.examineText = examineText;
    }

    public enum REASON{
        /**
         * 个人原因
         */
        Personal(0, "个人原因"),

        /**
         * 工作原因
         */
        Work(1, "工作原因"),

        /**
         * 年假
         */
        Annual_leave(2, "年假"),

        /**
         * 事假
         */
        Compassionate_leave(3, "事假"),

        /**
         * 病假
         */
        Sick_leave(4, "病假"),

        /**
         * 调休
         */
        Break_off(5, "调休"),

        /**
         * 产假
         */
        Maternity_leave(6, "产假"),

        /**
         * 陪产假
         */
        Paternity_leave(7, "陪产假"),

        /**
         * 婚假
         */
        Marriage_holiday(8, "婚假"),

        /**
         * 例假
         */
        Official_holiday(9, "例假"),

        /**
         * 丧假
         */
        Funeral_leave(10, "丧假"),

        /**
         * 哺乳假
         */
        Breastfeeding_leave(11, "哺乳假");

        private int index;

        private String name;

        REASON(int index, String name) {
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

    /**
     * 审核的类型
     */
    public enum EXAMINE_TYPE{
        /**
         * 请假审批
         */
        leave,

        /**
         * 请示批复
         */
        reply
    }
}
