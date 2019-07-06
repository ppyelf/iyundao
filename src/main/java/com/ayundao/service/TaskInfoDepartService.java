package com.ayundao.service;

import com.ayundao.entity.Task;
import com.ayundao.entity.TaskInfoDepart;

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
     * 根据任务id查找
     * @param id
     * @return
     */
    TaskInfoDepart findByTaskId(String id);

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
