package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Spirit
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 5:05
 * @Description: 实体 - 党内精神
 * @Version: V1.0
 */
@Entity
@Table(name = "t_spirit")
public class Spirit extends BaseEntity<String> {

    private final static long serialVersionUID = -1923749827189347L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 发布时间
     */
    @Column(name = "TIME")
    private String time;

    /**
     * 浏览数
     */
    @Column(name = "HOTS", columnDefinition = "tinyint(4) default 0")
    private int hots;

    /**
     * 审批状态
     */
    @Column(name = "STATE")
    private SPIRIT_TYPE type;

    /**
     * 审批人id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    /**
     * 正文
     */
    @OneToOne(mappedBy = "spirit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SpiritContent spiritContent;

    /**
     * 附件
     */
    @OneToMany(mappedBy = "spirit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SpiritFile> spiritFiles;

    /**
     * 图片
     */
    @OneToMany(mappedBy = "spirit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SpiritImage> spiritImages;

    /**
     * 发布机构
     */
    @OneToOne(mappedBy = "spirit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SpiritSubject spiritSubject;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHots() {
        return hots;
    }

    public void setHots(int hots) {
        this.hots = hots;
    }

    public SpiritContent getSpiritContent() {
        return spiritContent;
    }

    public void setSpiritContent(SpiritContent spiritContent) {
        this.spiritContent = spiritContent;
    }

    public Set<SpiritFile> getSpiritFiles() {
        return spiritFiles;
    }

    public void setSpiritFiles(Set<SpiritFile> spiritFiles) {
        this.spiritFiles = spiritFiles;
    }

    public Set<SpiritImage> getSpiritImages() {
        return spiritImages;
    }

    public void setSpiritImages(Set<SpiritImage> spiritImages) {
        this.spiritImages = spiritImages;
    }

    public SpiritSubject getSpiritSubject() {
        return spiritSubject;
    }

    public void setSpiritSubject(SpiritSubject spiritSubject) {
        this.spiritSubject = spiritSubject;
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

    public SPIRIT_TYPE getType() {
        return type;
    }

    public void setType(SPIRIT_TYPE type) {
        this.type = type;
    }

    public enum SPIRIT_TYPE{
        /**
         * 未通过
         */
        NO,

        /**
         * 已通过
         */
        YES,
    }
}
