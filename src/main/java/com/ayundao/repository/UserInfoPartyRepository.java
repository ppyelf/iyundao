package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoOther;
import com.ayundao.entity.UserInfoParty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserInfoPartyRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户详情 -党建基础表
 * @Version: V1.0
 */
@Repository
public interface UserInfoPartyRepository extends BaseRepository<UserInfoParty,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select ui from UserInfoParty ui where ui.userinfoid=?1")
    UserInfoParty findByUserInfoPartyUserid(String userinfoid);

    @Query("select ui from UserInfoParty ui where ui.type=?1")
    List<UserInfoParty> findByType(UserInfoParty.TYPE type);
}
