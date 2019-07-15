package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 试卷表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_testpaper")
public class Testpaper extends BaseEntity<String>{

    private static final long serialVersionUID = -6871981662538230548L;

    /**
     * 试卷名称
     */
    @Column(name = "NAME", length = 50)
    private String name;

    /**
     * 试卷简介
     */
    @Column(name = "INTRO",length = 50)
    private String intro;

    /**
     * 考试试卷表试卷id
     * @return
     */
    @OneToMany(mappedBy = "testpaper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoTextpaper> examInfoTextpaper;

    /**
     *试卷题目表试卷id
     * @return
     */
    @OneToMany(mappedBy = "testpaper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PaperTitle> paperTitle;

    /**
     * 考试人员详情表试卷id
     * @return
     */
    @OneToMany(mappedBy = "testpaper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoUser> examInfoUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Set<ExamInfoTextpaper> getExamInfoTextpaper() {
        return examInfoTextpaper;
    }

    public void setExamInfoTextpaper(Set<ExamInfoTextpaper> examInfoTextpaper) {
        this.examInfoTextpaper = examInfoTextpaper;
    }


    public Set<PaperTitle> getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(Set<PaperTitle> paperTitle) {
        this.paperTitle = paperTitle;
    }

    public Set<ExamInfoUser> getExamInfoUser() {
        return examInfoUser;
    }

    public void setExamInfoUser(Set<ExamInfoUser> examInfoUser) {
        this.examInfoUser = examInfoUser;
    }
}
