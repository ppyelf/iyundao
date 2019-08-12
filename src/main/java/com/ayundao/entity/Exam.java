package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 考试表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_exam")
public class Exam extends BaseEntity<String>{

    private static final long serialVersionUID = -2332071144602062784L;

    /**
     *  标题
     */
    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    /**
     * 考试开始时间
     */
    @Column(name = "STARTTIME", length = 50)
    private String startTime;

    /**
     * 开始结束时间
     */
    @Column(name = "OVERTIME", length = 50)
    private String overTime;

    /**
     * 考试时长
     */
    @Column(name = "EXAMLONG", length = 50)
    private String examLong;

    /**
     * 合格分数
     */
    @Column(name = "SCORE", length = 50)
    private String score;

    /**
     * 考试说明
     */
    @Column(name = "SHOWTHAT", length = 200)
    private String showThat;

    /**
     * 考试与部门组织考试id
     */
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoDepart> examInfoDepart;

    /**
     * 考试与试卷考试id
     */
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoTextpaper> examInfoTextpaper;

    /**
     * 考试人员得分表试卷id
     */
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoUserScore> examInfoUserScore;

    /**
     * 考试人员详情表考试id
     */
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExamInfoUser> examInfoUser;



    /**
     * 备用字段1
     */
    @Column(name = "INFO1", length = 50)
    private String info1;
    /**
     * 备用字段2
     */
    @Column(name = "INFO2", length = 50)
    private String info2;
    /**
     * 备用字段3
     */
    @Column(name = "INFO3", length = 50)
    private String info3;
    /**
     * 备用字段4
     */
    @Column(name = "INFO4", length = 50)
    private String info4;
    /**
     * 备用字段5
     */
    @Column(name = "INFO5", length = 50)
    private String info5;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getExamLong() {
        return examLong;
    }

    public void setExamLong(String examLong) {
        this.examLong = examLong;
    }

    public String getShowThat() {
        return showThat;
    }

    public void setShowThat(String showThat) {
        this.showThat = showThat;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    public Set<ExamInfoDepart> getExamInfoDepart() {
        return examInfoDepart;
    }

    public void setExamInfoDepart(Set<ExamInfoDepart> examInfoDepart) {
        this.examInfoDepart = examInfoDepart;
    }

    public Set<ExamInfoTextpaper> getExamInfoTextpaper() {
        return examInfoTextpaper;
    }

    public void setExamInfoTextpaper(Set<ExamInfoTextpaper> examInfoTextpaper) {
        this.examInfoTextpaper = examInfoTextpaper;
    }

    public Set<ExamInfoUserScore> getExamInfoUserScore() {
        return examInfoUserScore;
    }

    public void setExamInfoUserScore(Set<ExamInfoUserScore> examInfoUserScore) {
        this.examInfoUserScore = examInfoUserScore;
    }

    public Set<ExamInfoUser> getExamInfoUser() {
        return examInfoUser;
    }

    public void setExamInfoUser(Set<ExamInfoUser> examInfoUser) {
        this.examInfoUser = examInfoUser;
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
