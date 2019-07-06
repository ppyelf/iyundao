package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * 消息人员接收表
 * Created by 13620 on 2019/7/2.
 */
@Entity
@Table(name = "t_advices_info_user")
public class AdvicesInfoUser extends BaseEntity<String>{

    private static final long serialVersionUID = -8697749555339231366L;

    /**
     * 任务id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADVICESID", nullable = false)
    private Advices advices;

    /**
     * 用户id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    public Advices getAdvices() {
        return advices;
    }

    public void setAdvices(Advices advices) {
        this.advices = advices;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
