package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: Massage
 * @project: ayundao
 * @author: King
 * @Date: 2019/7/5 15:00
 * @Version: V1.0
 */
@Entity
@Table(name = "t_message")
public class Message extends BaseEntity<String> {

    private static final long serialVersionUID = -121545215454525L;

    /**
     * 消息发布附属文件
     */
    @OneToMany(mappedBy = "message",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<MessageFile> messageFiles;

    /**
     * 消息发布附属图片
     */
    @OneToMany(mappedBy = "message",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<MessageImage> messageImages;

    /**
     * 发布支部
     */
    @Column(name = "BRANCH",nullable = false,length = 50)
    private String branch;

    /**
     * 发布人员
     */
    @Column(name = "PUBLISHER",nullable = false,length = 50)
    private String publisher;

    /**
     * 要闻作者
     */
    @Column(name = "AUTHOR",nullable = false,length = 50)
    private String author;

    /**
     * 要闻时间
     */
    @Column(name = "ARTICLETIME",length = 50)
    private String articleTime;

    /**
     * 要闻简介
     */
    @Column(name = "ARTICLEINTRODUCE")
    private String articleIntroduce;

    /**
     * 要闻正文
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "ARTICLE")
    private String article;

    /**
     * 审核人ID
     */
    @Column(name = "USERID",unique = true)
    private String userId;

    /**
     * 审核人评价
     */
    @Column(name = "USERASSESS",length = 1000)
    private String userAssess;

    /**
     * 审核状态
     * 0 -未审核
     * 1 -审核通过
     * 2 -审核拒绝
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "TYPE",nullable = false)
    private TYPE type;

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

    public Set<MessageFile> getMessageFiles() {
        return messageFiles;
    }

    public void setMessageFiles(Set<MessageFile> messageFiles) {
        this.messageFiles = messageFiles;
    }

    public Set<MessageImage> getMessageImages() {
        return messageImages;
    }

    public void setMessageImages(Set<MessageImage> messageImages) {
        this.messageImages = messageImages;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticleTime() {
        return articleTime;
    }

    public void setArticleTime(String articleTime) {
        this.articleTime = articleTime;
    }

    public String getArticleIntroduce() {
        return articleIntroduce;
    }

    public void setArticleIntroduce(String articleIntroduce) {
        this.articleIntroduce = articleIntroduce;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAssess() {
        return userAssess;
    }

    public void setUserAssess(String userAssess) {
        this.userAssess = userAssess;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
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

    public enum TYPE{
        /**
         * 0 -未审核
         */
        NOT(0,"未审核"),
        /**
         * 1 -审核同意
         */
        YES(1,"审核通过"),
        /**
         * 2 -审核未通过
         */
        NO(2,"审核未通过");
        private int index;
        private String name;
        private TYPE(int index,String name){
            this.index=index;
            this.name=name;
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
