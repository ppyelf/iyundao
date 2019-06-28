package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: UserInfoParty
 * @project: ayundao
 * @author: king
 * @Date: 2019/6/11 15:00
 * @Description: 用户详情 -党员建设
 * @version: V1.0
 */
@Entity
@Table(name = "t_user_info_party")
public class UserInfoParty extends BaseEntity<String> {

    private static final long serialVersionUID = -1215421356564L;

    /**
      * 党员类型
      * 0 -未入党
      * 1 -党员
      * 2 -入党申请人
      * 3 -建档对象
      * 4 -入党积极分子
      * 5 -预备党员
      */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "TYPE",length = 11)
    private TYPE type;

    /**
      * 党籍状态
      * 0 -停止党籍
      * 1 -正常党籍
      */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATE",length = 11)
    private STATE state;

    /**
     * 党内职务
     */
    @Column(name = "PARTYPOST",length = 50)
    private String partyPost;

    /**
     * 党支部
     */
    @Column(name = "PARTYBRANCH",length = 50)
    private String partyBranch;

    /**
     * 入党申请时间
     */
    @Column(name = "APPLYDATE",length = 50)
    private String applyDate;

    /**
     * 建档对象时间
     */
    @Column(name = "POTDATE",length = 50)
    private String potDate;

    /**
     * 入党积极分子日期
     */
    @Column(name = "ACTIVISTDATE",length = 50)
    private  String activistDate;

    /**
     * 预备党员时间
     */
    @Column(name = "READYDATE",length = 50)
    private String readyDate;

    /**
     * 入党日期
     */
    @Column(name = "PARTYDATE",length = 50)
    private String partyDate;

    /**
     * 用户详情ID
     */
    @Column(name = "USERINFOID",nullable = false,length = 50)
    private String userinfoid;

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

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public String getPartyPost() {
        return partyPost;
    }

    public void setPartyPost(String partyPost) {
        this.partyPost = partyPost;
    }

    public String getPartyBranch() {
        return partyBranch;
    }

    public void setPartyBranch(String partyBranch) {
        this.partyBranch = partyBranch;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getPotDate() {
        return potDate;
    }

    public void setPotDate(String potDate) {
        this.potDate = potDate;
    }

    public String getActivistDate() {
        return activistDate;
    }

    public void setActivistDate(String activistDate) {
        this.activistDate = activistDate;
    }

    public String getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(String readyDate) {
        this.readyDate = readyDate;
    }

    public String getPartyDate() {
        return partyDate;
    }

    public void setPartyDate(String partyDate) {
        this.partyDate = partyDate;
    }

    public String getUserinfoid() {
        return userinfoid;
    }

    public void setUserinfoid(String userinfoid) {
        this.userinfoid = userinfoid;
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

    public enum TYPE {

        /**
         * 0 -未入党
         */
        NOTPARTY(0, "未入党"),

        /**
         * 1 -党员
         */
        PARTY(1, "党员"),

        /**
         * 2 -入党申请人
         */
        APPALPARTY(2, "入党申请人"),

        /**
         * 3 -建档对象
         */
        ARCHIVINGOBJECT(3,"建档对象"),
        /**
         * 4 -入党积极分子
         */
        POSITIVEPARTY(4, "入党积极分子"),
        /**
         * 5 -预备党员
         */
        READYPARTY(5, "预备党员");

        private int index;
        private String name;

        private TYPE(int index, String name) {
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

    public enum STATE{
        /**
         * 0 -停止党籍
         */
        STOP(0,"停止党籍"),
        /**
         * 1 -正常党籍
         */
        NORMAL(1,"正常党籍");
        private int index;
        private String name;
        private STATE(int index,String name){
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

