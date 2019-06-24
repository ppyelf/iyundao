package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.entity.UserInfo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoRepository
 * @project: ayundao
 * @author: king
 * @Date: 2019/6/11 17:00
 * @Description: 仓库 -- 用户详情
 * @Version: V1.0
 */
@Repository
public interface UserInfoRepository extends BaseRepository<UserInfo,String> {

    /**
     * 根据用户详情ID查询实体信息
     */
    @Query("select ui from UserInfo ui where ui.id = ?1")
    UserInfo findByUserInfoId(String id);

}



