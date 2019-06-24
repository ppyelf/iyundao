package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoPersonnel;
import com.ayundao.entity.UserInfoTitleaPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoTitleaPostRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户 -职称职务表
 * @Version: V1.0
 */
@Repository
public interface UserInfoTitleaPostRepository extends BaseRepository<UserInfoTitleaPost,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select ui from UserInfoTitleaPost ui where ui.userinfoid=?1")
    UserInfoTitleaPost findByUserInfoTitleaPostUserid(String userinfoid);
}
