package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: UserFile
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/5 8:55
 * @Description: 实体 - 用户资料
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_file")
public class UserFile extends BaseEntity<String> {

    private final static long serialVersionUID = -123809128309128093L;

    /**
     * 名称
     */
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 编号
     */
    @Column(name = "CODE", nullable = false, unique = true, length = 10)
    private long code;

    /**
     * URL
     */
    @Column(name = "URL", nullable = false)
    private String url;

    /**
     * 后缀名
     */
    @Column(name = "SUFFIX", nullable = false)
    private String suffix;

    /**
     * 是否公开
     */
    @Column(name = "ISPUBLIC", columnDefinition = "boolean default 0")
    private boolean isPublic;

    /**
     * 状态-审核
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS")
    private STATUS status;

    /**
     * 资源类型
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "TYPE")
    private TYPE type;

    /**
     * 用户
     */
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID")
    private User user;

    /**
     * 分享资源
     */
    @OneToMany(mappedBy = "userFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserFileTo> userFileTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<UserFileTo> getUserFileTo() {
        return userFileTo;
    }

    public void setUserFileTo(Set<UserFileTo> userFileTo) {
        this.userFileTo = userFileTo;
    }

    public enum TYPE{
        /**
         * 文档
         */
        word(0, "文档"),

        /**
         * 图片
         */
        image(1, "图片"),

        /**
         * 视频
         */
        video(2, "视频");

        private int index;

        private String name;

        TYPE(int index, String name) {
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

    public enum STATUS{
        /**
         * 待审核
         */
        waiting(0, "待审核"),

        /**
         * 分享
         */
        share(1, "分享"),

        /**
         * 个人
         */
        myself(2, "个人"),

        /**
         * 拒绝
         */
        refuse(3, "拒绝");

        private int index;

        private String name;

        STATUS(int index, String name) {
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
