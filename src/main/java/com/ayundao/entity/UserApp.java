package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import com.ayundao.base.utils.TimeUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: UserApp
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/20 9:44
 * @Description: 用户授权信息
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_app")
public class UserApp extends BaseEntity<String> {

    private final static long serialVersionUID = -183209481093280491L;

    /**
     * 第三方类型
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "APPTYPE", nullable = false)
    private APP_TYPE type;

    /**
     * 所属用户
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID")
    private User user;

    /**
     * openId
     */
    @Column(name = "OPENID", nullable = false, length = 50)
    private String openId;

    /**
     * 昵称
     */
    @Column(name = "NICKNAME", nullable = false, length = 50)
    private String nickName;

    /**
     * 省市
     */
    @Column(name = "CITY", length = 50)
    private String city;

    /**
     * 城镇
     */
    @Column(name = "PROVINCE", length = 50)
    private String province;

    /**
     * 头像
     */
    @Column(name = "AVATARURL")
    private String avatarUrl;

    /**
     * 唯一授权ID
     */
    @Column(name = "UNIONID")
    private String unionId;

    /**
     * 最后登录时间
     */
    @Column(name = "LASTLOGINTIME", nullable = false)
    private String lastLoginTime;

    public APP_TYPE getType() {
        return type;
    }

    public void setType(APP_TYPE type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = TimeUtils.convertTime(lastLoginTime, "yyyyMMddHHmmss");
    }

    public enum APP_TYPE{
        /**
         * 微信
         */
        WeChat(0, "微信"),

        /**
         * 小程序
         */
        WeApp(1, "微信小程序");

        private int index;

        private String name;

        APP_TYPE(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
