package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.AdvicesInfoUser;
import com.ayundao.entity.User;
import org.springframework.data.jpa.repository.Query;
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
public interface AdvicesInfoUserRepository extends BaseRepository<AdvicesInfoUser, String> {

    @Query("select a from AdvicesInfoUser a where a.advices.id = ?1")
    List<AdvicesInfoUser> findByAdvicesId(String id);

    @Query("select a from AdvicesInfoUser a where a.user = ?1 order by a.lastModifiedDate desc ")
    List<AdvicesInfoUser> findAllByUser(User user);

}
