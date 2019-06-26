package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

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
     * 发起时间
     */
    @Column(name = "TIME", nullable = false, length = 50)
    private String time;

    /**
     * 发起原因
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "REASON", nullable = false, length = 1)
    private EXAMINE_REASON reason;

    /**
     * 描述
     */
    @Column(name = "REAMRK", length = 500)
    private String remark;

    /**
     * 审核类型
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "EXAMINETYPE", length = 1, nullable = false)
    private EXAMINE_TYPE type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public EXAMINE_REASON getReason() {
        return reason;
    }

    public void setReason(EXAMINE_REASON reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public enum EXAMINE_REASON{
        /**
         * 个人原因
         */
        personal(0, "个人原因"),

        /**
         * 工作原因
         */
        work(1, "工作原因");

        private int index;

        private String name;

        EXAMINE_REASON(int index, String name) {
            this.index = index;
            this.name = name;
        }
    }

    /**
     * 审核的类型
     */
    public enum EXAMINE_TYPE{
        /**
         * 在线审批
         */
        online,

        /**
         * 请假
         */
        leave,

        /**
         * 请示批复
         */
        reply
    }
}
