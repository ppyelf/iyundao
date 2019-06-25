package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.base.Pageable;
import com.ayundao.entity.Page;
import com.ayundao.entity.UserInfoContract;
import com.ayundao.entity.UserInfoFdh;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserInfoFdhRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户详情 -妇代会表
 * @Version: V1.0
 */
@Repository
public interface UserInfoFdhRepository extends BaseRepository<UserInfoFdh,String> {

    /**
     * 根据用户ID查询实体信息
     *
     * @param userinfoid
     * @return
     */
    @Query("select uif from UserInfoFdh uif where uif.userinfoid = ?1")
    UserInfoFdh findByUserInfoFdhUserid(String userinfoid);

}
