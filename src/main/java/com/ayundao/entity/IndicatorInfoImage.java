package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: IndicatorInfoImage
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 17:14
 * @Description: 实体 - 指标详情图片
 * @Version: V1.0
 */
@Entity
@Table(name = "t_indicator_info_image")
public class IndicatorInfoImage extends BaseEntity<String> {

    private final static long serialVersionUID = -1309481023802198430L;

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
     * 描述
     */
    @Column(name = "REMARK", length = 500)
    private String remark;

    /**
     * 工作详情
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INDICATORINFOID")
    private IndicatorInfo indicatorInfo;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public IndicatorInfo getIndicatorInfo() {
        return indicatorInfo;
    }

    public void setIndicatorInfo(IndicatorInfo indicatorInfo) {
        this.indicatorInfo = indicatorInfo;
    }
}
