package com.ayundao.repository;

import com.ayundao.entity.ActivityInfoUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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

}
