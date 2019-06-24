package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoImage;
import com.ayundao.entity.UserInfoMedicalCare;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserInfoMedicalCareRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户 -医务护理表
 * @Version: V1.0
 */
@Repository
public interface UserInfoMedicalCareRepository extends BaseRepository<UserInfoMedicalCare,String> {
    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select ui from UserInfoMedicalCare ui where ui.userinfoid=?1")
    UserInfoMedicalCare findByUserInfoMedicalCareUserid(String userinfoid);
}
