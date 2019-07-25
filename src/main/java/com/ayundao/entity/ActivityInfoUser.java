package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * 活动人员报名表
 * Created by 13620 on 2019/7/2.
 */

@Entity
@Table(name = "t_activity_info_user")
public class ActivityInfoUser extends BaseEntity<String>{

    private static final long serialVersionUID = 2214396149423873159L;

    /**
     * 活动id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACTIVITY", nullable = false)
    private Activity activity;

    /**
     * 人员id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
