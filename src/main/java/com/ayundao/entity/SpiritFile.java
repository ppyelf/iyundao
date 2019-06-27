package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: SpiritFile
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 5:01
 * @Description: 实体 - 党内精神附件
 * @Version: V1.0
 */
@Entity
@Table(name = "t_spirit_file")
public class SpiritFile extends BaseEntity<String> {

    private final static long serialVersionUID = 1234809128409128094l;

    /**
     * 名称
     */
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    /**
     * URL
     */
    @Column(name = "URL", length = 100, nullable = false)
    private String url;

    /**
     * 后缀名
     */
    @Column(name = "SUFFIX", length = 5, nullable = false)
    private String suffix;

    /**
     * 党内精神
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SPIRITID")
    private Spirit spirit;

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

    public Spirit getSpirit() {
        return spirit;
    }

    public void setSpirit(Spirit spirit) {
        this.spirit = spirit;
    }
}
