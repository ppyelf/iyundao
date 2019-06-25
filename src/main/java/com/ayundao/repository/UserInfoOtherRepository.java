package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoMzdp;
import com.ayundao.entity.UserInfoOther;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoOtherRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户 -其他表
 * @Version: V1.0
 */
@Repository
public interface UserInfoOtherRepository extends BaseRepository<UserInfoOther,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select ui from UserInfoOther ui where ui.userinfoid=?1")
    UserInfoOther findByUserInfoOtherUserid(String userinfoid);
}
