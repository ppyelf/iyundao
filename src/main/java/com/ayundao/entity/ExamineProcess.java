package com.ayundao.entity;

import com.ayundao.base.BaseComponent;
import com.ayundao.base.BaseEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;

/**
 * @ClassName: ExamineProcess
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/22 10:53
 * @Description: 实体 - 审核流程
 * @Version: V1.0
 */
@Entity
@Table(name = "t_examine_process")
public class ExamineProcess extends BaseEntity<String> {

    private final static long serialVersionUID = -1320482109348093218L;

    /**
     * 机构
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "SUBJECTID")),
            @AttributeOverride(name = "name", column = @Column(name = "SUBJECTNAME"))
    })
    private BaseComponent subject;

    /**
     * 部门
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "DEPARTID")),
            @AttributeOverride(name = "name", column = @Column(name = "DEPARTNAME"))
    })
    private BaseComponent depart;

    /**
     * 小组
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "GROUPID")),
            @AttributeOverride(name = "name", column = @Column(name = "GROUPNAME"))
    })
    @Column(name = "GROUPID", length = 50)
    private BaseComponent group;

    /**
     * 用户
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "USERID")),
            @AttributeOverride(name = "name", column = @Column(name = "USERNAME"))
    })
    @Column(name = "USERID", nullable = false, length = 50)
    private BaseComponent user;

    /**
     * 所属审核
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EXAMINEID", nullable = false)
    private Examine examine;

    /**
     * 人员类型
     *       0-发起人,1-审核人,2-抄送人
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PERSONTYPE", nullable = false, length = 1)
    private PERSON_TYPE type;

    /**
     * 审核状态:0-审核中,1-同意,2-拒绝
     */
    @Enumerated
    @Column(name = "STATUS", nullable = false, length = 1)
    private PROCESS_STATUS status;

    /**
     * 评语
     */
    @Column(name = "COMMENT")
    private String comment;

    /**
     * 评审等级
     */
    @Column(name = "LEVEL", nullable = false, columnDefinition = "tinyint(1) default 0")
    private int level;

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

    public BaseComponent getSubject() {
        return subject;
    }

    public void setSubject(BaseComponent subject) {
        this.subject = subject;
    }

    public BaseComponent getDepart() {
        return depart;
    }

    public void setDepart(BaseComponent depart) {
        this.depart = depart;
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

    public Examine getExamine() {
        return examine;
    }

    public void setExamine(Examine examine) {
        this.examine = examine;
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

    public PERSON_TYPE getType() {
        return type;
    }

    public void setType(PERSON_TYPE type) {
        this.type = type;
    }

    public PROCESS_STATUS getStatus() {
        return status;
    }

    public void setStatus(PROCESS_STATUS status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public enum PERSON_TYPE{
        /**
         * 发起人
         */
        Initiator(0, "发起人"),

        /**
         * 审批人
         */
        Examiner(1, "审批人"),

        /**
         * 抄送人
         */
        Copier(2, "抄送人");

        private int index;

        private String name;

        PERSON_TYPE(int index, String name) {
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

    public enum PROCESS_STATUS{
        /**
         * 审核中
         */
        Audit_in_progress(0, "审核中"),

        /**
         * 同意
         */
        Agree(1, "同意"),

        /**
         * 拒绝
         */
        refuse(2, "拒绝"),

        /**
         * 查看
         */
        view(3, "查看"),

        /**
         * 通过
         */
        pass(4, "通过");

        private int index;

        private String name;

        PROCESS_STATUS(int index, String name) {

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
