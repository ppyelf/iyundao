package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: ActivityFile
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 8:41
 * @Description: 实体 - 活动文件
 * @Version: V1.0
 */
@Entity
@Table(name = "t_activity_file")
public class ActivityFile extends BaseEntity<String> {

    private final static long serialVersionUID = -1324791283409812398L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * url
     */
    @Column(name = "URL", length = 100)
    private String url;

    /**
     * 后缀名
     */
    @Column(name = "SUFFIX", length = 4)
    private String suffix;

    /**
     * 类型
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "TYPE")
    private ACTIVITY_FILE_TYPE type;

    /**
     * 内容
     */
    @Column(name = "CONTENT", length = 500)
    private String content;

    /**
     * 来源
     */
    @Column(name = "FROMTO", length = 50)
    private String fromTo;

    /**
     * 所属活动
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACTIVITYID", nullable = false)
    private Activity activity;

    /**
     * 浏览次数
     */
    @Column(name = "HOTS", columnDefinition = "tinyint default 0", nullable = false)
    private int hots;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromTo() {
        return fromTo;
    }

    public void setFromTo(String fromTo) {
        this.fromTo = fromTo;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getHots() {
        return hots;
    }

    public void setHots(int hots) {
        this.hots = hots;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public ACTIVITY_FILE_TYPE getType() {
        return type;
    }

    public void setType(ACTIVITY_FILE_TYPE type) {
        this.type = type;
    }

    public enum ACTIVITY_FILE_TYPE{
        /**
         * 文档
         */
        text,

        /**
         * 文件
         */
        file
    }
}


