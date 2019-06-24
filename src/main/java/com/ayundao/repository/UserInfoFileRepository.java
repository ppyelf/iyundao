package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoFileRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户详情 -附件表
 * @Version: V1.0
 */
@Repository
public interface UserInfoFileRepository extends BaseRepository<UserInfoFile,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select uif from UserInfoFile uif where uif.userinfoid=?1")
    UserInfoFile findByUserInfoFileUserid(String userinfoid);
}
