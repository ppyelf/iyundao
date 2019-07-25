package com.ayundao.service.impl;

import com.ayundao.entity.Task;
import com.ayundao.entity.TaskInfoDepart;
import com.ayundao.entity.User;
import com.ayundao.repository.TaskInfoDepartRepository;
import com.ayundao.repository.TaskRepository;
import com.ayundao.repository.UserRepository;
import com.ayundao.service.TaskInfoDepartService;
import com.ayundao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: TaskInfoDepartServiceImpl
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/4
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Service
@Transactional
public class TaskInfoDepartServiceImpl implements TaskInfoDepartService {

    @Autowired
    private TaskInfoDepartRepository taskInfoDepartRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<TaskInfoDepart> findByTaskId(String id) {
        return taskInfoDepartRepository.findByTaskId(id);
    }

    @Override
    @Transactional
    public void delete(TaskInfoDepart tid) {
        taskInfoDepartRepository.delete(tid);
    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        User user = task.getUser();
        user.setTask(null);
        userRepository.save(user);
        taskRepository.delete(task);
    }


}
