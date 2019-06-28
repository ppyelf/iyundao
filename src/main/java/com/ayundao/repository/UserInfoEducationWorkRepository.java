package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoEducationWork;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserInfoEducationWorkRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户 -教育工作表
 * @Version: V1.0
 */
@Repository
public interface UserInfoEducationWorkRepository extends BaseRepository<UserInfoEducationWork,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select uie from UserInfoEducationWork uie where uie.userinfoid = ?1")
    List<UserInfoEducationWork> findByUserInfoEducationWorkUserid(String userinfoid);
}
