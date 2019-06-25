package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoTitleaPost;
import com.ayundao.entity.UserInfoTw;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoTwRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户详情 -团委表
 * @Version: V1.0
 */
@Repository
public interface UserInfoTwRepository extends BaseRepository<UserInfoTw,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select ui from UserInfoTw ui where ui.userinfoid=?1")
    UserInfoTw findByUserInfoTwUserid(String userinfoid);
}
