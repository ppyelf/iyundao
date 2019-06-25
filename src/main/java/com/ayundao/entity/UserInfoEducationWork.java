package com.ayundao.entity;

import com.ayundao.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: UserInfoEducationWork
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/11 13:50
 * @Description: 用户详情 -教育工作表
 * @Version: V1.0
 */
@Entity
@Table(name = "t_user_info_educationwork")

public class UserInfoEducationWork extends BaseEntity<String> {

    private static final long serialVerisionUID = -1245515455111L;

    /**
     * 开始日期
     */
    @Column(name = "STARTDATE",length = 50)
    private String startdate;

    /**
     * 毕业日期
     */
    @Column(name = "ENDDATE",length = 50)
    private String enddate;

    /**
     * 毕业学校
     */
    @Column(name = "GRADUATIONSCHOOL",length = 50)
    private String graduationschool;

    /**
     * 所学专业
     */
    @Column(name = "MAJOR",length = 50)
    private String major;

    /**
     * 教育类别
     */
    @Column(name = "EDUCATIONCATEGORY",length = 50)
    private String educationcategory;

    /**
     * 学历
     */
    @Column(name = "EDUCATION",length = 50)
    private String education;

    /**
     * 学位
     */
    @Column(name = "DEGREE",length = 50)
    private String degree;

    /**
     * 学位时间
     */
    @Column(name = "DEGREEDATE",length = 50)
    private String degreedate;

    /**
     * 学制
     */
    @Column(name = "EDUSYSTEM",length = 50)
    private String edusystem;

    /**
     * 证明人
     */
    @Column(name = "WITNESS",length = 50)
    private String witness;

    /**
     * 用户详情ID
     */
    @Column(name = "USERINFOID",nullable = false,length = 50)
    private String userinfoid;

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

    public static long getSerialVerisionUID() {
        return serialVerisionUID;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getGraduationschool() {
        return graduationschool;
    }

    public void setGraduationschool(String graduationschool) {
        this.graduationschool = graduationschool;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducationcategory() {
        return educationcategory;
    }

    public void setEducationcategory(String educationcategory) {
        this.educationcategory = educationcategory;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegreedate() {
        return degreedate;
    }

    public void setDegreedate(String degreedate) {
        this.degreedate = degreedate;
    }

    public String getEdusystem() {
        return edusystem;
    }

    public void setEdusystem(String edusystem) {
        this.edusystem = edusystem;
    }

    public String getWitness() {
        return witness;
    }

    public void setWitness(String witness) {
        this.witness = witness;
    }

    public String getUserinfoid() {
        return userinfoid;
    }

    public void setUserinfoid(String userinfoid) {
        this.userinfoid = userinfoid;
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
