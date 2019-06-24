package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoMedicalCare;
import com.ayundao.entity.UserInfoMzdp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoMzdpRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户详情 -民主党派表
 * @Version: V1.0
 */
@Repository
public interface UserInfoMzdpRepository extends BaseRepository<UserInfoMzdp,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select ui from UserInfoMzdp ui where ui.userinfoid=?1")
    UserInfoMzdp findByUserInfoMzdpUserid(String userinfoid);
}
