package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * 考题答案表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_paper_answer")
public class PaperAnswer extends BaseEntity<String>{

    private static final long serialVersionUID = 7621070214334722065L;

    /**
     * 题目id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PAPERTITLEID", nullable = false)
    private PaperTitle paperTitle;

    /**
     * 考题序号
     */
    @Column(name = "SERIALID" , length = 50)
    private  String serialid;

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

    public PaperTitle getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(PaperTitle paperTitle) {
        this.paperTitle = paperTitle;
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
