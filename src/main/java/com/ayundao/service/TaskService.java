package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Task;
import com.ayundao.entity.TaskInfoDepart;

import java.util.List;

/**
 * 任务
 * Created by 13620 on 2019/7/3.
 */
public interface TaskService {

    /**
     * 查询所有任务
     */
    List<Task> findAll();

    /**
     * 添加任务
     */
    Task save(Task task,String subjectId, String departId, String groupId);

    /**
     * 根据id查找
     */
    Task find (String id);

    /**
     * 删除实体
     */
    void delete (Task task);

    /**
     * 根据部门组织id查询任务实体
     * @param id
     * @return
     */
    List<Task> findAdvicesByDeptionId(String id);
}
