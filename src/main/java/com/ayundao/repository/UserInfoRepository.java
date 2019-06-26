package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.entity.UserInfo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserInfoRepository
 * @project: ayundao
 * @author: king
 * @Date: 2019/6/11 17:00
 * @Description: 仓库 -- 用户详情
 * @Version: V1.0
 */
@Repository
public interface UserInfoRepository extends BaseRepository<UserInfo,String> {

    /**
     * 根据用户详情ID查询实体信息
     */
    @Query("select ui from UserInfo ui where ui.id = ?1")
    UserInfo findByUserInfoId(String id);

    /**
     * 查询党建用户详情男女比例
     */
    @Query(value = "select count(case when ui.sex = '男' then 1 else null end ) as man," +
            "count(case when ui.sex = '女' then 1 else null end) as woman from t_user_info ui " +
            "inner join t_user_info_party uip on ui.id = uip.userinfoid where uip.type = '1'",nativeQuery = true)
    Map<String,Object> countBySex();

    /**
     * 查询用户详情学历比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.`EDUCATION` = '高中' THEN 1 ELSE NULL END ) AS highSchool,\n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '专科' THEN 1 ELSE NULL END) AS specialty,\n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '本科' THEN 1 ELSE NULL END) AS undergraduate, \n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '研究生' THEN 1 ELSE NULL END) AS postgraduate, \n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '博士' THEN 1 ELSE NULL END) AS doctor          \n" +
            "       FROM t_user_info ui\n" +
            "       INNER JOIN t_user_info_party uip ON ui.id = uip.userinfoid WHERE uip.type = '1'",nativeQuery = true)
    Map<String,Object> countByEducation();
//
//    /**
//     * 查询用户详情职称比例
//     */
//    @Query("select count(ui.title) from UserInfo ui where ui.title = ?1")
//    Integer countByTitle(String title);
//
//    /**
//     * 查询用户详情支部比例
//     */
//    @Query("select count(ui.branchName) from UserInfo ui where ui.branchName = ?1")
//    Integer countByBranchName(String branchName);
//
    /**
     * 查询用户详情科室比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.department = '内科' THEN 1 ELSE NULL END) AS nternal,\n" +
            "COUNT(CASE WHEN ui.department = '外科' THEN 1 ELSE NULL END) AS surgery,\n" +
            "COUNT(CASE WHEN ui.department = '中医科' THEN 1 ELSE NULL END) AS chinese,\n" +
            "COUNT(CASE WHEN ui.department = '眼科' THEN 1 ELSE NULL END) AS eye,\n" +
            "COUNT(CASE WHEN ui.department = '急诊科' THEN 1 ELSE NULL END) AS emergency\n" +
            "FROM t_user_info ui INNER JOIN t_user_info_party uip ON ui.id = uip.userinfoid WHERE uip.type = '1'",nativeQuery = true)
    Map<String,Object> countByDepartment();

//    /**
//     * 查询用户详情党龄比例
//     */
//    @Query(value = "",nativeQuery = true)
//    Map<String,Object> countByPartyDate();

    /**
     * 查询用户详情年龄比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN (YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4)) <=25 THEN 1 ELSE NULL END ) AS '25周岁以下',\n" +
            "COUNT(CASE WHEN 25<(YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4))<=35 THEN 1 ELSE NULL END ) AS '25-35周岁',\n" +
            "COUNT(CASE WHEN 35<(YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4))<=45 THEN 1 ELSE NULL END ) AS '35-45以下',\n" +
            "COUNT(CASE WHEN 45<(YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4)) THEN 1 ELSE NULL END ) AS '45周岁以上'      \n" +
            "       FROM t_user_info ui\n" +
            "       INNER JOIN t_user_info_party uip ON ui.id = uip.userinfoid WHERE uip.type = '1'",nativeQuery = true)
    Map<String,Object> countByIdcard();
}




