package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: MessageFile
 * @project: ayundao
 * @author: king
 * @Date: 2019/6/11 15:00
 * @Description: 消息分布文件
 * @version: V1.0
 */
@Entity
@Table(name = "t_message_file")
public class MessageFile extends BaseEntity<String> {

    private static final long serialVersionUID = -12154541326568L;

    /**
     * 文件名称
     */
    @Column(name = "NAME",nullable = false,length = 50)
    private String name;

    /**
     * 附件URL路径
     */
    @Column(name = "URL",nullable = false)
    private String url;

    /**
     * 文件后缀名
     */
    @Column(name = "SUFFIX",nullable = false)
    private String suffix;

    /**
     * 消息发布ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MESSAGEID")
    private Message message;

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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
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
}
