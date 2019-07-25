package com.ayundao.service;

import com.ayundao.entity.Task;
import com.ayundao.entity.TaskInfoDepart;

import java.util.List;

/**
 * @ClassName: TaskInfoDepartService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/4
 * @Description: 任务关联部门组织机构
 * @Version: V1.0
 */
public interface TaskInfoDepartService {

    /**
     * 根据任务id查找关系
     * @param id
     * @return
     */
    List<TaskInfoDepart> findByTaskId(String id);

    /**
     * 删除实体
     * @param tid
     */
    void delete(TaskInfoDepart tid);

    /**
     * 删除任务实体
     * @param task
     */
    void deleteTask(Task task);


}
