package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
@Entity
@Table(name = "t_user_info_mzdp")

public class UserInfoMzdp extends BaseEntity<String> {

    private static final long serialVersionUID = -1548466563264L;

    /**
     * 民主党派
     * 0 -中国国民党革命委员会
     * 1 -中国民主同盟
     * 2 -中国民主建国会
     * 3 -中国民主促进会
     * 4 -中国农工民主党
     * 5 -中国致公党
     * 6 -九三学社
     * 7 -台湾民主自治同盟
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "DEMOCRATICPARTIES", length = 11)
    private DEMOCRATICPARTIES democraticparties;

    /**
     * 入党时间
     */
    @Column(name = "TIME",length = 255)
    private String time;

    /**
     * 党内职务
     */
    @Column(name = "PARTYPOST")
    private String partyPost;

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

    public DEMOCRATICPARTIES getDemocraticparties() {
        return democraticparties;
    }

    public void setDemocraticparties(DEMOCRATICPARTIES democraticparties) {
        this.democraticparties = democraticparties;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPartyPost() {
        return partyPost;
    }

    public void setPartyPost(String partyPost) {
        this.partyPost = partyPost;
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

    public enum DEMOCRATICPARTIES {

        /**
         * 0 -中国国民党革命委员会
         */
        GOUMINDANG(0, "中国国民党革命委员会"),

        /**
         * 1 -中国民主同盟
         */
        MINZHUTONGMENG(1, "中国民主同盟"),

        /**
         * 2 -中国民主建国会
         */
        MINZHUJIANGUOHUI(2, "中国民主建国会"),

        /**
         * 3 -中国民主促进会
         */
        MINZHUCUJINHUI(3, "中国民主促进会"),
        /**
         * 4 -中国农工民主党
         */
        GONGNONGMINZHUDANG(4, "中国农工民主党"),
        /**
         * 5 -中国致公党
         */
        ZHIGONGDANG(5, "中国致公党"),
        /**
         * 6 -九三学社
         */
        JIUSANXUESHE(6, "九三学社"),
        /**
         * 7 -台湾民主自治同盟
         */
        TAIWANMINZHUZIZHI(7, "台湾民主自治同盟");
        private int index;

        private String name;

        private DEMOCRATICPARTIES(int index, String name) {
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
