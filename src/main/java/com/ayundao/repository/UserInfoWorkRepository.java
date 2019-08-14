package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoWork;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserInfoWorkRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户 -工作经历表
 * @Version: V1.0
 */
@Repository
public interface UserInfoWorkRepository extends BaseRepository<UserInfoWork,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select ui from UserInfoWork ui where ui.userinfoid=?1")
    List<UserInfoWork> findByUserInfoWorkUserid(String userinfoid);

    /**
     * 根据用户关联ID查询实体信息
     * @param userid
     * @return
     */
    @Query("select ui from UserInfoWork ui where ui.userinfoid=?1")
    UserInfoWork findbyUserInfoId(String userid);
}
