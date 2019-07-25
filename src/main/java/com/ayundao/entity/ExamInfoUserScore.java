package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * 考试人员得分表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_exam_info_user_score")
public class ExamInfoUserScore extends BaseEntity<String>{

    private static final long serialVersionUID = 7928810391586913651L;

    /**
     * 考试id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EXAMID", nullable = false)
    private Exam exam;

    /**
     * 用户id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    /**
     * 得分
     */
    @Column(name = "SCORE", length = 50)
    private String score;


    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
