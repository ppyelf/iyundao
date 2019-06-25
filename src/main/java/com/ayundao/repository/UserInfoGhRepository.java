package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoFile;
import com.ayundao.entity.UserInfoGh;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
