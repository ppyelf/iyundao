package com.ayundao.entity;

import com.ayundao.base.BaseComponent;
import com.ayundao.base.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: UserFileTo
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/5 14:07
 * @Description: 实体 - 用户资源分享源
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_file_to")
public class UserFileTo extends BaseEntity<String> {

    private final static long serialVersionUID = -1231083901823091L;

    /**
     * 所属资料
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERRESOURCESID", nullable = false)
    private UserFile userFile;

    /**
     * 所属机构
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "SUBJECTID")),
            @AttributeOverride(name = "name", column = @Column(name = "SUBJECTNAME"))
    })
    private BaseComponent subject;

    /**
     * 所属部门
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "DEPARTID")),
            @AttributeOverride(name = "name", column = @Column(name = "DEPARTNAME"))
    })
    private BaseComponent depart;

    /**
     * 所属小组
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "GROUPID")),
            @AttributeOverride(name = "name", column = @Column(name = "GROUPNAME"))
    })
    private BaseComponent group;

    /**
     * 分享给的用户
     */
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "USERID")),
            @AttributeOverride(name = "name", column = @Column(name = "USERNAME"))
    })
    private BaseComponent user;

    public UserFile getUserFile() {
        return userFile;
    }

    public void setUserFile(UserFile userFile) {
        this.userFile = userFile;
    }

    public BaseComponent getSubject() {
        return subject;
    }

    public void setSubject(BaseComponent subject) {
        this.subject = subject;
    }

    public BaseComponent getDepart() {
        return depart;
    }

    public void setDepart(BaseComponent depart) {
        this.depart = depart;
    }

    public BaseComponent getGroup() {
        return group;
    }

    public void setGroup(BaseComponent group) {
        this.group = group;
    }

    public BaseComponent getUser() {
        return user;
    }

    public void setUser(BaseComponent user) {
        this.user = user;
    }
}
