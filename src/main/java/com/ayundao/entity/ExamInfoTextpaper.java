package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_exam_info_textpaper")
public class ExamInfoTextpaper extends BaseEntity<String>{

    private static final long serialVersionUID = -2334007252732881122L;

    /**
     * 考试id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EXAMID", nullable = false)
    private Exam exam;

    /**
     * 试卷id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TESTPAPER",nullable = false)
    private Testpaper testpaper;

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
}
