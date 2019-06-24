package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoBasic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/**
 * @ClassName: UserInfoBasicRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户 -基本信息表
 * @Version: V1.0
 */
@Repository
public interface UserInfoBasicRepository extends BaseRepository<UserInfoBasic,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select uib from UserInfoBasic uib where uib.userinfoid = ?1")
    UserInfoBasic findByUserInfoBasicUserId(String userinfoid);

}
