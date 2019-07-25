package com.ayundao.service;

import com.alibaba.fastjson.JSONArray;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.*;

import java.util.List;
import java.util.Map;

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

    /**
     * 添加任务
     */
    Task save(Task task, String[] subjectIds, String[] departIds, String[] groupIds, String[] userids);

    /**
     * 发送任务
     * @param taskInfoDeparts
     */
    List<User> sendtask(List<TaskInfoDepart> taskInfoDeparts);

    /**
     * 查看是否以及发送过任务
     * @param id
     */
    List<TaskInfoUser> findsentistrue(String id);

    /**
     * 改变状态
     * @param id
     */
    void updatstate(String id,String state);

    /**
     *通过实体找到电话号码
     * @param userList
     * @return
     */
    Map<String,String> findphoneByUser(List<User> userList);
}
