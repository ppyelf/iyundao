package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.TaskInfoDepart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: TaskInfoDepartRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/3
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface TaskInfoDepartRepository  extends BaseRepository<TaskInfoDepart, String> {

    //根据任务id查找所有部门组织机构信息
    @Query("select a from TaskInfoDepart a where a.task.id =?1")
    List<TaskInfoDepart> findByTaskId(String id);








}
