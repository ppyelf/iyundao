package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.TaskInfoUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: TaskInfoUserRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/9
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface TaskInfoUserRepository extends BaseRepository<TaskInfoUser,String> {
    @Query("select a from TaskInfoUser a where a.task.id = ?1")
    List<TaskInfoUser> findsentistrue(String id);
}
