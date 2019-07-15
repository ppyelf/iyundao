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
     * 条件查询
     * @param name
     * @param number
     * @param department
     * @return
     */
    @Query("select ui from UserInfo ui where ui.name like ?1 or ui.number like ?2 or ui.department like ?3")
    List<UserInfo> findByNameOrNumberOrDepartmentLike(String name,String number, String department);

//    /**
//     * 根据部门id查询
//     * @param id 部门id
//     * @return
//     */
//    @Query(value = "select ui.* from t_user_info ui left join t_user u on ui.userid = u.id " +
//                   "left join t_user_relations ur on u.id=ur.userid where ur=#{ur.id}}",nativeQuery = true)
//    List<UserInfo> findByUserid(String id);
//
////    @Query(value = "select ui.* from t_user_info ui left join t_user u on ui.userid = u.id" +
////                   "left join t_user_relations ur on u.id=ur.userid where ur=#{ur.id}}",nativeQuery = true)


    /**
     * 查询党建用户详情男女比例
     */
    @Query(value = "select count(case when ui.sex = '男' then 1 else null end ) as man," +
            "count(case when ui.sex = '女' then 1 else null end) as woman from t_user_info ui " +
            "inner join t_user_info_party uip on ui.id = uip.userinfoid where uip.type = '1'",nativeQuery = true)
    Map<String,Object> countBySex();

    /**
     * 查询党建用户详情学历比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.`EDUCATION` = '高中' THEN 1 ELSE NULL END ) AS highSchool,\n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '专科' THEN 1 ELSE NULL END) AS specialty,\n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '本科' THEN 1 ELSE NULL END) AS undergraduate, \n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '研究生' THEN 1 ELSE NULL END) AS postgraduate, \n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '博士' THEN 1 ELSE NULL END) AS doctor          \n" +
            "       FROM t_user_info ui\n" +
            "       INNER JOIN t_user_info_party uip ON ui.id = uip.userinfoid WHERE uip.type = '1'",nativeQuery = true)
    Map<String,Object> countByEducation();

    /**
     * 查询用户详情年龄比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN (YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4)) <=25 THEN 1 ELSE NULL END ) AS '25周岁以下',\n" +
            "            COUNT(CASE WHEN (YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4)) BETWEEN 25 AND 35 THEN 1 ELSE NULL END ) AS '25-35周岁',\n" +
            "            COUNT(CASE WHEN (YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4)) BETWEEN 35 AND 45 THEN 1 ELSE NULL END ) AS '35-45以下',\n" +
            "            COUNT(CASE WHEN 45<=(YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4)) THEN 1 ELSE NULL END ) AS '45周岁以上'\n" +
            "                  FROM t_user_info ui\n" +
            "                   INNER JOIN t_user_info_party uip ON ui.id = uip.userinfoid WHERE uip.type = '1'",nativeQuery = true)
    Map<String,Object> countByIdcard();

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

    /**
     * 查询用户详情党龄比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN (YEAR(NOW()) - SUBSTRING(ui.`PARTYDATE`,1,4)) <=2 THEN 1 ELSE NULL END ) AS '2年以下',\n" +
            "            COUNT(CASE WHEN (YEAR(NOW()) - SUBSTRING(ui.`PARTYDATE`,1,4)) BETWEEN 2 AND 5 THEN 1 ELSE NULL END ) AS '2-5年',\n" +
            "            COUNT(CASE WHEN (YEAR(NOW()) - SUBSTRING(ui.`PARTYDATE`,1,4)) BETWEEN 5 AND 10 THEN 1 ELSE NULL END ) AS '5-10年',\n" +
            "            COUNT(CASE WHEN 10<=(YEAR(NOW()) - SUBSTRING(ui.`PARTYDATE`,1,4)) THEN 1 ELSE NULL END ) AS '10年以上'\n" +
            "            FROM t_user_info ui INNER JOIN t_user_info_party uip ON ui.id = uip.userinfoid WHERE uip.type = '1'\n",nativeQuery = true)
    Map<String,Object> countByPartyAge();

    /**
     * 查询用户详情职称比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.`TITLE` = '主任医师' THEN 1 ELSE NULL END ) AS chiefPhysician,\n" +
            "       COUNT(CASE WHEN ui.`TITLE` = '副主任医师' THEN 1 ELSE NULL END) AS deputyChiefPhysician,\n" +
            "       COUNT(CASE WHEN ui.`TITLE` = '主治医师' THEN 1 ELSE NULL END) AS attendingDoctor,\n" +
            "       COUNT(CASE WHEN ui.`TITLE` = '住院医师' THEN 1 ELSE NULL END) AS residents,\n" +
            "       COUNT(CASE WHEN ui.`TITLE` = '医师' THEN 1 ELSE NULL END) AS doctor\n" +
            "       FROM t_user_info ui INNER JOIN t_user_info_party uip ON ui.id = uip.userinfoid WHERE uip.type = '1'",nativeQuery = true)
    Map<String,Object> countByTitle();

    /**
     * 查询用户详情支部比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.`BRANCHNAME` = '第一党支部' THEN 1 ELSE NULL END ) AS ONE,\n" +
            "       COUNT(CASE WHEN ui.`BRANCHNAME` = '第二党支部' THEN 1 ELSE NULL END) AS two,\n" +
            "       COUNT(CASE WHEN ui.`BRANCHNAME` = '第三党支部' THEN 1 ELSE NULL END) AS three,\n" +
            "       COUNT(CASE WHEN ui.`BRANCHNAME` = '第四党支部' THEN 1 ELSE NULL END) AS four,\n" +
            "       COUNT(CASE WHEN ui.`BRANCHNAME` = '第五党支部' THEN 1 ELSE NULL END) AS five\n" +
            "       FROM t_user_info ui INNER JOIN t_user_info_party uip ON ui.id = uip.userinfoid WHERE uip.type = '1'",nativeQuery = true)
    Map<String,Object> countByBranch();

    /**
     * 党建籍贯比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.`PLACE` LIKE '%杭州%' THEN 1 ELSE NULL END ) AS yes,\n" +
            "       COUNT(CASE WHEN ui.`PLACE` NOT LIKE '%杭州%' THEN 1 ELSE NULL END ) AS NO     \n" +
            "       FROM t_user_info ui INNER JOIN t_user_info_party uip ON ui.id = uip.userinfoid WHERE uip.type = '1'",nativeQuery = true)
    Map<String,Object> countByPlace();

    /**
     * 党建身份比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.`IDENTITY`='干部' THEN 1 ELSE NULL END ) AS cadre,\n" +
                    "COUNT(CASE WHEN ui.`IDENTITY`='群众' THEN 1 ELSE NULL END ) AS masses\n" +
                    " FROM t_user_info ui INNER JOIN t_user_info_party uip ON ui.id = uip.userinfoid WHERE uip.type = '1'",nativeQuery = true)
    Map<String,Object> countByIdentity();

    /**
     * 根据userid寻找实体
     * @param id
     * @return
     */
    @Query("select a from UserInfo a where a.userid = ?1")
    UserInfo findByUserId(String id);
}




