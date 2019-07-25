package com.ayundao.repository;

import com.ayundao.entity.Activity;
import com.ayundao.entity.ActivityInfoUser;
import com.ayundao.entity.AdvicesInfoUser;
import com.ayundao.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ActivityInfoUserRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/6
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface ActivityInfoUserRepository extends CrudRepository<ActivityInfoUser, String>{

    @Query("select a from  ActivityInfoUser a where a.activity =?1 AND a.user in ?2")
    List<ActivityInfoUser> findActivityInfoUserByUserAndActivity(Activity activity, List<User> user);

    @Query("select a.user from ActivityInfoUser a where a.activity = ?1")
    List<User> findUserFromActivityInfroUserByActivity(Activity activity);

    @Query("select a from ActivityInfoUser a where a.user in ?1")
    List<ActivityInfoUser> findBYusers(List<User> users);


}
