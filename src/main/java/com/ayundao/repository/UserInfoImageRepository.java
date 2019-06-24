package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoFile;
import com.ayundao.entity.UserInfoImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoImageRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户详情 -图片表
 * @Version: V1.0
 */
@Repository
public interface UserInfoImageRepository extends BaseRepository<UserInfoImage,String> {
    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select ui from UserInfoImage ui where ui.userinfoid=?1")
    UserInfoImage findByUserInfoImageUserid(String userinfoid);
}
