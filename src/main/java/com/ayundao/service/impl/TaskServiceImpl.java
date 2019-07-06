package com.ayundao.service.impl;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.*;
import com.ayundao.repository.SubjectRepository;
import com.ayundao.repository.TaskInfoDepartRepository;
import com.ayundao.repository.TaskRepository;
import com.ayundao.service.DepartService;
import com.ayundao.service.GroupsService;
import com.ayundao.service.SubjectService;
import com.ayundao.service.TaskService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by 13620 on 2019/7/3.
 */
@Component
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

    @Override
    public List<Task> findAll() {

        return taskRepository.findAllForList();
    }

    @Override
      @Modifying
    @Transactional
    public Task save(Task task, String subjectId, String departId, String groupId) {
        task = taskRepository.save(task);
        TaskInfoDepart tid = new TaskInfoDepart();
        tid.setCreatedDate(new Date());
        tid.setLastModifiedDate(new Date());

        if (StringUtils.isNotBlank(subjectId)){
            Subject subject = subjectService.find(subjectId);
            tid.setSubject(subject);
        }
        if(StringUtils.isNotBlank(departId)){
           Depart depart = departService.findById(departId);
           tid.setDepart(depart);
        }
        if(StringUtils.isNotBlank(groupId)){
          Groups groups = groupsService.findById(groupId);
          tid.setGroups(groups);
        }
        tid.setTask(task);
        System.out.println("tid:"+tid);
        taskInfoDepartRepository.save(tid);
        System.out.println("task:"+task);
        return task;
    }

    @Override
    public Task find(String id) {
        return taskRepository.find(id);
    }

    @Override
    @Transactional
    public void delete(Task task) {
//        TaskInfoDepart tid = taskInfoDepartRepository.findByTaskId(task.getId());
//        taskInfoDepartRepository.delete(tid);
//        taskRepository.delete(task);
    }

    @Override
    public List<Task> findAdvicesByDeptionId(String id) {

        return taskRepository.findAdvicesByDeptionId(id);
    }


}
