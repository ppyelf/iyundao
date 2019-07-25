package com.ayundao.repository;

import com.ayundao.entity.Activity;
import com.ayundao.entity.AdvicesInfoUser;
import com.ayundao.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AdvicesInfoUserRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/9
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface AdvicesInfoUserRepository extends CrudRepository<AdvicesInfoUser, String> {

    @Query("select a from AdvicesInfoUser a where a.advices.id = ?1")
    List<AdvicesInfoUser> findByAdvicesId(String id);

    @Query("select a from AdvicesInfoUser a where a.user = ?1 order by a.lastModifiedDate desc ")
    List<AdvicesInfoUser> findAllByUser(User user);

}
