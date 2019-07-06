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

    /**
     * 考题序号
     */
    @Column(name = "SERIALID", length = 50)
    private String serialid;

    /**
     * 考试答案选择
     */
    @Column(name = "ANSWERSELECT", length = 50)
    private  String answerselect;

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

    public String getSerialid() {
        return serialid;
    }

    public void setSerialid(String serialid) {
        this.serialid = serialid;
    }

    public String getAnswerselect() {
        return answerselect;
    }

    public void setAnswerselect(String answerselect) {
        this.answerselect = answerselect;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
