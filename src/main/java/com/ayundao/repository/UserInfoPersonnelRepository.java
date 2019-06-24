package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoParty;
import com.ayundao.entity.UserInfoPersonnel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoPersonnelRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户 -人事信息表
 * @Version: V1.0
 */
@Repository
public interface UserInfoPersonnelRepository extends BaseRepository<UserInfoPersonnel,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select ui from UserInfoPersonnel ui where ui.userinfoid=?1")
    UserInfoPersonnel findByUserInfoPersonnelUserid(String userinfoid);
}
