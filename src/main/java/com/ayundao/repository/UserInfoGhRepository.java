package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoFile;
import com.ayundao.entity.UserInfoGh;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @ClassName: UserInfoGhRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户详情 -工会表
 * @Version: V1.0
 */
@Repository
public interface UserInfoGhRepository extends BaseRepository<UserInfoGh,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select uig from UserInfoGh uig where uig.userinfoid=?1")
    UserInfoGh findByUserInfoGhUserid(String userinfoid);

    /**
     * 查询工会详情男女比例
     */
    @Query(value = "select count(case when ui.sex = '男' then 1 else null end ) as man," +
            "count(case when ui.sex = '女' then 1 else null end) as woman from t_user_info ui " +
            "inner join t_user_info_gh uig on ui.id = uig.userinfoid ",nativeQuery = true)
    Map<String,Object> countBySexGh();

    /**
     * 查询工会详情学历比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.`EDUCATION` = '高中' THEN 1 ELSE NULL END ) AS highSchool,\n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '专科' THEN 1 ELSE NULL END) AS specialty,\n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '本科' THEN 1 ELSE NULL END) AS undergraduate, \n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '研究生' THEN 1 ELSE NULL END) AS postgraduate, \n" +
            "       COUNT(CASE WHEN ui.`EDUCATION` = '博士' THEN 1 ELSE NULL END) AS doctor          \n" +
            "       FROM t_user_info ui\n" +
            "       INNER JOIN t_user_info_gh uig on ui.id = uig.userinfoid",nativeQuery = true)
    Map<String,Object> countByEducationGh();

    /**
     * 查询工会年龄比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN (YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4)) <=25 THEN 1 ELSE NULL END ) AS '25周岁以下',\n" +
            "            COUNT(CASE WHEN (YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4)) BETWEEN 25 AND 35 THEN 1 ELSE NULL END ) AS '25-35周岁',\n" +
            "            COUNT(CASE WHEN (YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4)) BETWEEN 35 AND 45 THEN 1 ELSE NULL END ) AS '35-45以下',\n" +
            "            COUNT(CASE WHEN 45<=(YEAR(NOW())- SUBSTRING(ui.`IDCARD`,7,4)) THEN 1 ELSE NULL END ) AS '45周岁以上'\n" +
            "                  FROM t_user_info ui\n" +
            "                   INNER JOIN t_user_info_gh uig on ui.id = uig.userinfoid",nativeQuery = true)
    Map<String,Object> countByIdcardGh();

    /**
     * 查询工会详情科室比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.department = '内科' THEN 1 ELSE NULL END) AS nternal,\n" +
            "COUNT(CASE WHEN ui.department = '外科' THEN 1 ELSE NULL END) AS surgery,\n" +
            "COUNT(CASE WHEN ui.department = '中医科' THEN 1 ELSE NULL END) AS chinese,\n" +
            "COUNT(CASE WHEN ui.department = '眼科' THEN 1 ELSE NULL END) AS eye,\n" +
            "COUNT(CASE WHEN ui.department = '急诊科' THEN 1 ELSE NULL END) AS emergency\n" +
            "FROM t_user_info ui INNER JOIN t_user_info_gh uig on ui.id = uig.userinfoid",nativeQuery = true)
    Map<String,Object> countByDepartmentGh();

    /**
     * 查询工会详情工龄比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN (YEAR(NOW()) - SUBSTRING(uig.`TIME`,1,4)) <=2 THEN 1 ELSE NULL END ) AS '2年以下',\n" +
            "            COUNT(CASE WHEN (YEAR(NOW()) - SUBSTRING(uig.`TIME`,1,4)) BETWEEN 2 AND 5 THEN 1 ELSE NULL END ) AS '2-5年',\n" +
            "            COUNT(CASE WHEN (YEAR(NOW()) - SUBSTRING(uig.`TIME`,1,4)) BETWEEN 5 AND 10 THEN 1 ELSE NULL END ) AS '5-10年',\n" +
            "            COUNT(CASE WHEN 10<=(YEAR(NOW()) - SUBSTRING(uig.`TIME`,1,4)) THEN 1 ELSE NULL END ) AS '10年以上'\n" +
            "            FROM t_user_info ui INNER JOIN t_user_info_gh uig on ui.id = uig.userinfoid",nativeQuery = true)
    Map<String,Object> countByPartyAgeGh();

    /**
     * 查询工会详情职称比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.`TITLE` = '主任医师' THEN 1 ELSE NULL END ) AS chiefPhysician,\n" +
            "       COUNT(CASE WHEN ui.`TITLE` = '副主任医师' THEN 1 ELSE NULL END) AS deputyChiefPhysician,\n" +
            "       COUNT(CASE WHEN ui.`TITLE` = '主治医师' THEN 1 ELSE NULL END) AS attendingDoctor,\n" +
            "       COUNT(CASE WHEN ui.`TITLE` = '住院医师' THEN 1 ELSE NULL END) AS residents,\n" +
            "       COUNT(CASE WHEN ui.`TITLE` = '医师' THEN 1 ELSE NULL END) AS doctor\n" +
            "       FROM t_user_info ui INNER JOIN t_user_info_gh uig on ui.id = uig.userinfoid",nativeQuery = true)
    Map<String,Object> countByTitleGh();

    /**
     * 工会籍贯比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.`PLACE` LIKE '%杭州%' THEN 1 ELSE NULL END ) AS yes,\n" +
            "       COUNT(CASE WHEN ui.`PLACE` NOT LIKE '%杭州%' THEN 1 ELSE NULL END ) AS NO     \n" +
            "       FROM t_user_info ui INNER JOIN t_user_info_gh uig on ui.id = uig.userinfoid",nativeQuery = true)
    Map<String,Object> countByPlaceGh();

    /**
     * 工会身份比例
     */
    @Query(value = "SELECT COUNT(CASE WHEN ui.`IDENTITY`='干部' THEN 1 ELSE NULL END ) AS cadre,\n" +
            "COUNT(CASE WHEN ui.`IDENTITY`='群众' THEN 1 ELSE NULL END ) AS masses\n" +
            " FROM t_user_info ui INNER JOIN t_user_info_gh uig on ui.id = uig.userinfoid",nativeQuery = true)
    Map<String,Object> countByIdentityGh();
}
