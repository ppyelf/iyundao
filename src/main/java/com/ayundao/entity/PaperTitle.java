package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 试卷题目表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_paper_title")
public class PaperTitle extends BaseEntity<String>{

    private static final long serialVersionUID = 7886967886530131619L;

    /**
     * 试卷id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TESTPAPERID", nullable = false)
    private Testpaper testpaper;

//    /**
//     * 考题序号
//     */
//    @Column(name = "SERIALID" , length = 50)
//    private  String serialid;

    /**
     * 考试内容介绍
     */
    @Column(name = "EXAMCONTENT", length = 50)
    private String examContent;

    /**
     * 类型
     */
    @Column(name = "TYPE", length = 50)
    private String type;

    /**
     * 本题分数
     */
    @Column(name = "SCORE", length = 50)
    private String score;

    /**
     *考题答案表考题id
     * @return
     */
    @OneToMany(mappedBy = "paperTitle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PaperAnswer> paperAnswer;

    /**
     *考试人员详情表考题id
     * @return
     */
    @OneToMany(mappedBy = "paperTitle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoUser> examInfoUser;


    public Testpaper getTestpaper() {
        return testpaper;
    }

    public void setTestpaper(Testpaper testpaper) {
        this.testpaper = testpaper;
    }

//    public String getSerialid() {
//        return serialid;
//    }
//
//    public void setSerialid(String serialid) {
//        this.serialid = serialid;
//    }


    public Set<ExamInfoUser> getExamInfoUser() {
        return examInfoUser;
    }

    public void setExamInfoUser(Set<ExamInfoUser> examInfoUser) {
        this.examInfoUser = examInfoUser;
    }

    public String getExamContent() {
        return examContent;
    }

    public void setExamContent(String examContent) {
        this.examContent = examContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Set<PaperAnswer> getPaperAnswer() {
        return paperAnswer;
    }

    public void setPaperAnswer(Set<PaperAnswer> paperAnswer) {
        this.paperAnswer = paperAnswer;
    }
}
