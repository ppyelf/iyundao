package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.repository.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 13620 on 2019/7/3.
 */
@Component
@Transactional
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskInfoDepartRepository taskInfoDepartRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private TaskInfoUserRepository taskInfoUserRepository;


    @Override
    public List<Task> findAll() {

        return taskRepository.findAllForList();
    }

    @Override
    @Transactional
    public Task save(Task task, String[] subjectIds, String[] departIds, String[] groupIds, String[] userids) {
        task = taskRepository.save(task);
        TaskInfoDepart tid;
        Subject subject;
        Depart depart;
        Groups groups;
        User user;
        //分别添加部门组织机构用户
            for (int i =0;i<subjectIds.length;i++){
                tid = new TaskInfoDepart();
                tid.setCreatedDate(new Date());
                tid.setLastModifiedDate(new Date());
                tid.setTask(task);
                subject = subjectService.find(subjectIds[i]);
                tid.setSubject(subject);
                taskInfoDepartRepository.save(tid);
            }
            for(int j = 0;j<departIds.length;j++){
                tid = new TaskInfoDepart();
                tid.setCreatedDate(new Date());
                tid.setLastModifiedDate(new Date());
                tid.setTask(task);
                depart = departService.findById(departIds[j]);
                tid.setDepart(depart);
                taskInfoDepartRepository.save(tid);
            }
            for (int k = 0;k<groupIds.length;k++){
                tid = new TaskInfoDepart();
                tid.setCreatedDate(new Date());
                tid.setLastModifiedDate(new Date());
                tid.setTask(task);
                groups = groupsService.findById(groupIds[k]);
                tid.setGroups(groups);
                taskInfoDepartRepository.save(tid);
            }
            for (int u = 0;u<userids.length;u++){
                tid = new TaskInfoDepart();
                tid.setCreatedDate(new Date());
                tid.setLastModifiedDate(new Date());
                tid.setTask(task);
                user = userService.findById(userids[u]);
                tid.setUser(user);
                taskInfoDepartRepository.save(tid);
            }
        return task;
    }

    @Override
    @Transactional
    public List<User> sendtask(List<TaskInfoDepart> taskInfoDeparts) {
        TaskInfoUser tiu;
        List<User> userList = new ArrayList<>();
        for (TaskInfoDepart taskInfoDepart : taskInfoDeparts) {
            //如果是机构获取所有部门组织
            if (taskInfoDepart.getSubject()!=null){
                List<User> users = userService.findBySubjectIdForPage(taskInfoDepart.getSubject().getId());
                userList.addAll(users);
                saveUser(taskInfoDepart,users);
            }

            if (taskInfoDepart.getDepart()!= null){
                        List<User>  users = userService.findByDepartIdForPage(taskInfoDepart.getDepart().getId());
                userList.addAll(users);
                        saveUser(taskInfoDepart,users);
            }
            if (taskInfoDepart.getGroups()!=null){
                        List<User> users = userService.findByGroupIdForPage(taskInfoDepart.getGroups().getId());
                userList.addAll(users);
                        saveUser(taskInfoDepart,users);

            }
            //如果有用户
            if(taskInfoDepart.getUser()!=null){
                userList.add(taskInfoDepart.getUser());
                UserInfo uif =userInfoRepository.findByUserId(taskInfoDepart.getUser().getId());
                tiu = new TaskInfoUser();
                tiu.setCreatedDate(new Date());
                tiu.setLastModifiedDate(new Date());
                tiu.setTask(taskInfoDepart.getTask());
                tiu.setUser(taskInfoDepart.getUser());
                tiu.setState("未接收");
                //这一块如果角色没有建立关系，或报错
                tiu.setPhone(uif.getPhone());
                taskInfoUserRepository.save(tiu);
            }
        }
            return userList;
    }

    @Override
    public  List<TaskInfoUser> findsentistrue(String id) {

        return taskInfoUserRepository.findsentistrue(id);
    }

    @Override
    public void updatstate(String id,String state) {
        taskRepository.updatestate(id,state);
    }

    @Override
    public JSONArray findphoneByUser(List<User> userList) {
        JSONArray jsonArray = new JSONArray();
        for (User user : userList) {
            if (StringUtils.isNotBlank(userInfoRepository.findphoneByUserId(user.getId()))){
                jsonArray.add(userInfoRepository.findphoneByUserId(user.getId()));
            }
        }
       return jsonArray;

    }


    @Override
    public Task find(String id) {
        return taskRepository.find(id);
    }

    @Override
    @Transactional
    public void delete(Task task) {

    }

    @Override
    public List<Task> findAdvicesByDeptionId(String id) {

        return taskRepository.findAdvicesByDeptionId(id);
    }

    private void saveUser(TaskInfoDepart taskInfoDepart,List<User> users){
        for (User user : users) {
            UserInfo uif =userInfoRepository.findByUserId(user.getId());
            TaskInfoUser   tiu= new TaskInfoUser();
            tiu.setCreatedDate(new Date());
            tiu.setLastModifiedDate(new Date());
            tiu.setTask(taskInfoDepart.getTask());
            tiu.setUser(user);
            tiu.setState("未接收");
            tiu.setPhone(uif.getPhone());
            taskInfoUserRepository.save(tiu);
        }
    }


}
