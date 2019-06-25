package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoFile;
import com.ayundao.entity.UserInfoGzqt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoGzqtRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户详情 -高知群体表
 * @Version: V1.0
 */
@Repository
public interface UserInfoGzqtRepository extends BaseRepository<UserInfoGzqt,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select uig from UserInfoGzqt uig where uig.userinfoid=?1")
    UserInfoGzqt findByUserInfoGzqtUserid(String userinfoid);
}
