package com.ayundao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Task;
import com.ayundao.entity.TaskInfoDepart;
import com.ayundao.entity.User;
import com.ayundao.repository.TaskInfoDepartRepository;
import com.ayundao.repository.TaskRepository;
import com.ayundao.service.TaskInfoDepartService;
import com.ayundao.service.TaskService;
import com.ayundao.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * 控制层- 任务
 * Created by 13620 on 2019/7/3.
 */

@RestController
@RequestMapping("/task")
public class TaskController extends BaseController{

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskInfoDepartService taskInfoDepartService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public JsonResult list(){
       List<Task> tasks =  taskService.findAll();
        JSONArray arr = new JSONArray();
        for(Task task : tasks){
            JSONObject object = new JSONObject();
            object.put("id",task.getId());
           object.put("title",task.getName());
            object.put("name",task.getUser().getName());
            object.put("type",task.getType());
            object.put("issuertime",task.getIssuertime());
            object.put("tasktext",task.getTasktext());
            if(task.getSendstate() ==null){
                task.setSendstate("未发送任务");
            }
            object.put("sendstate",task.getSendstate());
            arr.add(object);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * 添加
     */
    @PostMapping("/add")
    private JsonResult add(String name,
                           String type,
                           String issuertime,
                           String tasktext,
                           String userid,
                           String subjectId,
                           String departId,
                           String groupId ){
            if(StringUtils.isBlank(name)){
                return JsonResult.failure(601, "任务名称不能为空");
            }
            Task task = new Task();
            task.setCreatedDate(new Date());
            task.setLastModifiedDate(new Date());
            task.setName(name);
            task.setType(type);
        User user = userService.findById(userid);
            task.setUser(user);
            task.setIssuertime(issuertime);
            task.setTasktext(tasktext);
            task = taskService.save(task,subjectId,departId,groupId);
            jsonResult.setData(converTask(task));
        return jsonResult;
    }

    /**
     *根据id查找任务信息
     */
    @PostMapping("/view")
    public JsonResult view(String id){
        Task task = taskService.find(id);
        TaskInfoDepart taskInfoDepart = taskInfoDepartService.findByTaskId(id);
        if (task == null){
            return jsonResult.notFound("任务不存在");
        }
        System.out.println("task:"+task);
        System.out.println("TaskInfoDepart:"+taskInfoDepart);
        JSONObject object = new JSONObject();
        object.put("title",task.getName());
        object.put("workType",task.getType());
        object.put("issuertime",task.getTasktext());
        object.put("name",task.getUser().getName());
        object.put("tasktext",task.getTasktext());
        object.put("sendstatu",task.getSendstate());
        if(taskInfoDepart.getDepart() != null ){
            object.put("departId",taskInfoDepart.getDepart().getName());
        }
        if (taskInfoDepart.getSubject() != null){
            object.put("subjectId",taskInfoDepart.getSubject().getName());
        }
       if(taskInfoDepart.getGroups() != null){
           object.put("groupId",taskInfoDepart.getGroups().getName());
       }
//        jsonResult.setData(converTask(task));
        System.out.println(object);
        jsonResult.setData(object);
        return jsonResult;

    }


    /**
     * 根据id删除任务
     */
    @PostMapping("/del")
    public JsonResult del(String id){
        Task task = taskService.find(id);
        taskInfoDepartService.deleteTask(task);
        return JsonResult.success();
    }


    /**
     * 通过部门组织机构寻找任务
     */
    @PostMapping("/findBydeption")
    public JsonResult findBydeption(String id){
        List<Task> tasksss = taskService.findAdvicesByDeptionId(id);
        JSONArray arr = new JSONArray();
        JSONObject object;
        for (Task task : tasksss){
            object = new JSONObject();
            object.put("id",task.getId());
            object.put("title",task.getName());
            object.put("name",task.getUser().getName());
            object.put("type",task.getType());
            object.put("issuertime",task.getIssuertime());
            object.put("tasktext",task.getTasktext());
            if(task.getSendstate() ==null){
                task.setSendstate("未发送任务");
            }
            object.put("sendstate",task.getSendstate());
            arr.add(object);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }


    /**
     * 转换Task为json
     */
    private JSONObject converTask(Task task){
        JSONObject json = new JSONObject(JsonUtils.getJson(task));
        return json;
    }


}
