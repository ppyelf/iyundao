package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoImage;
import com.ayundao.entity.UserInfoLtxlgb;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoLtxlgbRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户详情 -离退休老干部表
 * @Version: V1.0
 */
@Repository
public interface UserInfoLtxlgbRepository extends BaseRepository<UserInfoLtxlgb,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select ui from UserInfoLtxlgb ui where ui.userinfoid=?1")
    UserInfoLtxlgb findByUserInfoLtxlgbUserid(String userinfoid);
}
