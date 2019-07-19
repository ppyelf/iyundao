package com.ayundao.repository;

import com.ayundao.entity.Sign;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: SignRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/6 10:10
 * @Description: 仓库 - 签到
 * @Version: V1.0
 */
@Repository
public interface SignRepository extends CrudRepository<Sign, String> {

    //根据活动ID获取实体集合
    @Query("select s from Sign s where s.activity.id = ?1")
    List<Sign> findByActivityId(String id);

    @Query("select s from Sign s where s.userId=?1 and s.SIGN_TYPE = ?2")
    List<Sign> findAllByUserId(String userid, Sign.SIGN_TYPE type);
}
