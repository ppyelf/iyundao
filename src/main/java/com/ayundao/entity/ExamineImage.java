package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.naming.Name;
import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;

/**
 * @ClassName: ExamineImage
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/1 10:07
 * @Description: 实体 - 审核图片
 * @Version: V1.0
 */
@Entity
@Table(name = "t_examine_image")
public class ExamineImage extends BaseEntity<String> {

    private static final long serialVersionUID = -138918048556010834L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * url
     */
    @Column(name = "URL", nullable = false, length = 100)
    private String url;

    /**
     * 后缀名
     */
    @Column(name = "SUFFIX", nullable = false, length = 4)
    private String suffix;

    /**
     * 所属审核
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAMINEID")
    private Examine examine;

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

    public Examine getExamine() {
        return examine;
    }

    public void setExamine(Examine examine) {
        this.examine = examine;
    }
}
