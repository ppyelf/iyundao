package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.UserInfoContract;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserInfoContractRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 用户 -合同信息表
 * @Version: V1.0
 */
@Repository
public interface UserInfoContractRepository extends BaseRepository<UserInfoContract,String> {

    /**
     * 根据用户ID查询实体信息
     * @param userinfoid
     * @return
     */
    @Query("select uic from UserInfoContract uic where uic.userinfoid = ?1")
    List<UserInfoContract> findByUserInfoContractUserid(String userinfoid);
}
