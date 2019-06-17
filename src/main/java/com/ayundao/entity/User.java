package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import com.ayundao.base.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: User
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/15 9:39
 * @Description: 用户
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity<String> {

    private static final long serialVersionUID = -1172094710974098503L;

    /**
     * 账号
     */
    @Column(name = "ACCOUNT", nullable = false, unique = true, length = 50)
    private String account;

    /**
     * 姓名
     */
    @Column(name ="NAME", nullable = false,length = 50)
    private String name;

    /**
     * 密码
     */
    @Column(name = "PASSWORD", nullable = false, length = 50)
    private String password;

    /**
     * 密码盐
     */
    @Column(name = "SALT", nullable = false, length = 50)
    private String salt;

    /**
     * 性别 0-男, 1-女
     */
    @Column(name = "SEX")
    private int sex;

    /**
     * 账号状态
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS")
    private ACCOUNT_TYPE status;

    /**
     * 用户类型
     */
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "USERTYPE")
    private USER_TYPE userType;

    /**
     * 用户简介
     */
    @Column(name = "REMARK", columnDefinition = "varchar(20) default '未填写'", length = 500)
    private String remark;

    /**
     * 机构关系
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRelation> userRelations;

    /**
     * 用户组关系
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserGroupRelation> userGroupRelations;

    /**
     * 角色关系
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles;

    /**
     * 部门负责人
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Depart> departs;

    /**
     * 小组负责人
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Groups> groups;

    /**
     * 备用字段1
     */
    @Column(name = "INFO1")
    private String info1;

    /**
     * 备用字段2
     */
    @Column(name = "INFO2")
    private String info2;

    /**
     * 备用字段3
     */
    @Column(name = "INFO3")
    private String info3;

    /**
     * 备用字段4
     */
    @Column(name = "INFO4")
    private String info4;

    /**
     * 备用字段5
     */
    @Column(name = "INFO5")
    private String info5;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = UUID.randomUUID().toString().replace("-","").substring(0, 16);
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public ACCOUNT_TYPE getStatus() {
        return status;
    }

    public void setStatus(ACCOUNT_TYPE status) {
        this.status = status;
    }

    public USER_TYPE getUserType() {
        return userType;
    }

    public void setUserType(USER_TYPE userType) {
        this.userType = userType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<UserRelation> getUserRelations() {
        return userRelations;
    }

    public void setUserRelations(Set<UserRelation> userRelations) {
        this.userRelations = userRelations;
    }

    public Set<UserGroupRelation> getUserGroupRelations() {
        return userGroupRelations;
    }

    public void setUserGroupRelations(Set<UserGroupRelation> userGroupRelations) {
        this.userGroupRelations = userGroupRelations;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<Depart> getDeparts() {
        return departs;
    }

    public void setDeparts(Set<Depart> departs) {
        this.departs = departs;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    /**
     * 账号类型
     */
    public enum ACCOUNT_TYPE {
        /**
         * 禁用
         */
        disable,

        /**
         * 锁定
         */
        block,

        /**
         * 正常
         */
        normal
    }

    /**
     * 用户类型
     */
    public enum USER_TYPE {
        /**
         * 普通用户
         */
        normal,

        /**
         * 管理员
         */
        admin,

        /**
         * 负责人
         */
       manager
    }
}
