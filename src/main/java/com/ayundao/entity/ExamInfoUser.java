package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * 考试人员详情表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_exam_info_user")
public class ExamInfoUser  extends BaseEntity<String>{

    private static final long serialVersionUID = -9153958282058600024L;

    /**
     * 题目id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PAPERTITLEID", nullable = false)
    private PaperTitle paperTitle;

    /**
     *考试id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EXAMID", nullable = false)
    private Exam exam;

    /**
     * 试卷id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TESTPAPERID", nullable = false)
    private Testpaper testpaper;

    /**
     * 用户id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

//    /**
//     * 考题序号
//     */
//    @Column(name = "SERIALID", length = 50)
//    private String serialid;

    /**
     * 考试答案选择
     */
    @Column(name = "ANSWERSELECT", length = 50)
    private  String answerSelect;

    /**
     * 是否为正确答案
     */
    @Column(name = "ANSWER", length = 50)
    private String answer;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Testpaper getTestpaper() {
        return testpaper;
    }

    public void setTestpaper(Testpaper testpaper) {
        this.testpaper = testpaper;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public String getSerialid() {
//        return serialid;
//    }
//
//    public void setSerialid(String serialid) {
//        this.serialid = serialid;
//    }


    public PaperTitle getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(PaperTitle paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getAnswerSelect() {
        return answerSelect;
    }

    public void setAnswerSelect(String answerSelect) {
        this.answerSelect = answerSelect;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
