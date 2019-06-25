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
     * 查询用户详情男女比例
     */
    @Query(value = "select sum (case when ui.sex = '男' then 1 else 0 end ) as man," +
            "sum(case when ui.sex = '女' then 1 else 0 end) as woman from t_user_info ui " +
            "inner join t_user_info_party uip on ui.id = uip.userinfoid where uip.type = '党员'",nativeQuery = true)
    Map<String,Integer> countBySex();

//    /**
//     * 查询用户详情学历比例
//     */
//    @Query("select count(ui.education) from UserInfo ui where ui.education = ?1" )
//    Integer countByEducation(String education);
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
//    /**
//     * 查询用户详情科室比例
//     */
//    @Query("select count(ui.department) from UserInfo ui where ui.department = ?1")
//    Integer countByDepartment(String department);
//
//    /**
//     * 查询用户详情党龄比例
//     */
//    @Query("")
//
//    /**
//     * 查询用户详情年龄比例
//     */
//    @Query("select count() from UserInfo ui where ui.")
}




