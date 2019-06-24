package com.ayundao.entity;

import com.ayundao.base.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName: UserInfo
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/11 13:50
 * @Description: 用户详情
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_info")
public class UserInfo extends BaseEntity<String> {

    private static final long serialVerisonUID = -123151154481254L;

    /**
     * 登录名
     */
    @Column(name = "USERNAME", nullable=false, unique = true, length = 50)
    private String username;

    /**
     * 密码
     */
    @Column(name = "PASSWORD", nullable=false, length = 50)
    private String password;

    /**
     * 行政编号
     */
    @Column(name = "NUMBER", nullable=false, unique = true, length = 50)
    private String number;

    /**
     * 姓名
     */
    @Length(min = 0, max = 50, message = "userinfo.name.length")
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    /**
     * 支部名称
     */
    @Column(name = "BRANCHNAME",nullable = false,length = 50)
    private String branchName;

    /**
     * 性别 0-男 1-女
     */
    @Column(name = "SEX",nullable = false,length = 2)
    private String sex;

    /**
     * 科室
     */
    @Column(name = "DEPARTMENT",nullable = false,length = 50)
    private String department;

    /**
     * 出生日期
     */
    @Column(name = "BIRTHDAY",nullable = false,length = 50)
    private String birthday;

    /**
     * 学历
     */
    @Column(name = "EDUCATION",nullable = false,length = 50)
    private String education;

    /**
     * 籍贯
     */
    @Column(name = "PLACE",nullable = false,length = 50)
    private String place;

    /**
     * 民族
     */
    @Column(name = "NATION",nullable = false,length = 50)
    private String nation;

    /**
     * 职务
     */
    @Column(name = "POST",nullable = false,length = 50)
    private String post;

    /**
     * 职称
     */
    @Column(name = "TITLE",length = 50)
    private String title;

    /**
     * 个人身份
     */
    @Column(name = "IDENTITY",length = 50)
    private String idEntity;

    /**
     * 工作时间
     */
    @Column(name = "WORKDATE",length = 50)
    private String workDate;

    /**
     * 入党时间
     */
    @Column(name = "PARTYDATE",length = 50)
    private String partyDate;

    /**
     * 转正时间
     */
    @Column(name = "CORRECTIONDATE",length = 50)
    private String correctionDate;

    /**
     * 联系电话
     */
    @Column(name = "PHONE",length = 11)
    private String phone;

    /**
     * 身份证号
     */
    @Column(name = "IDCARD",nullable = false,length = 18)
    private String idcard;

    /**
     * 用户ID
     */
    @Column(name = "USERID",nullable = false,length = 50)
    private String userid;

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

    public static long getSerialVerisonUID() {
        return serialVerisonUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(String idEntity) {
        this.idEntity = idEntity;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getPartyDate() {
        return partyDate;
    }

    public void setPartyDate(String partyDate) {
        this.partyDate = partyDate;
    }

    public String getCorrectionDate() {
        return correctionDate;
    }

    public void setCorrectionDate(String correctionDate) {
        this.correctionDate = correctionDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }
}
