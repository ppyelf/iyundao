package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: TaskRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/3
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface TaskRepository extends BaseRepository<Task, String> {

    //查找所有任务
    @Query("select a from Task a")
    List<Task> findAllForList();

    @Query("select a FROM Task a WHERE a.id in " +
            "(select b.task.id FROM TaskInfoDepart b WHERE b.subject.id = ?1 \n" +
            "OR b.depart.id = ?1 \n" +
            "OR b.groups.id = ?1)")
    List<Task> findAdvicesByDeptionId(String id);

    @Modifying
    @Query(value = "update t_task set sendstate = ?2 where id=?1",nativeQuery = true)
    void updatestate(String id,String state);
}
