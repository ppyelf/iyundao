package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.HttpUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Null;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ayundao.base.BaseController.*;

/**
 * 控制层- 任务
 * Created by 13620 on 2019/7/3.
 */
@RequiresUser
@RequiresRoles(value = {ROLE_ADMIN, ROLE_USER, ROLE_MANAGER, ROLE_USER, ROLE_PUBLISHER}, logical = Logical.OR)
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private DepartService departService;

    @Autowired
    private TaskInfoDepartService taskInfoDepartService;

    /**
     * @api {GET} /task/list 列表
     * @apiGroup Task
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求样例:
     * /task/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     * "data": [{"issuertime": "2019-07-11 00:00:00","name": "钱正","sendstate": "未发送任务","id": "4028d8816bbc4897016bbc6731640000","title": "王柏","type": "0","tasktext": "1223123"}]
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @GetMapping("/list")
    public JsonResult list() {
        List<Task> tasks = taskService.findAll();
        JSONArray arr = new JSONArray();
        for (Task task : tasks) {
            JSONObject object = new JSONObject();
            object.put("id", task.getId());
            object.put("title", task.getTitle());
            object.put("name", task.getUser().getName());
            object.put("type", task.getType());
            object.put("issuertime", task.getIssuertime());
            object.put("tasktext", task.getTasktext());
            if (task.getSendstate() == null) {
                task.setSendstate("未发送任务");
            }
            object.put("sendstate", task.getSendstate());
            arr.add(object);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /task/add 新增
     * @apiGroup Task
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} title 标题 必填
     * @apiParam {String} type 任务类型
     * @apiParam {String} issuertime 发布时间
     * @apiParam {String} tasktext  任务内容
     * @apiParam {String} userid 发布人员id 必填
     * @apiParam {String[]} subjectIds 机构id   每一条部门类型的只需要最子集部门
     * @apiParam {String[]} departIds   部门id
     * @apiParam {String[]} groupIds    组织id
     * @apiParam {String[]} userids 用户id
     * @apiParamExample {json} 请求样例:
     *                /task/add?title=任务名称&type=1&issuertime=2018-12-12 12:12:12&tasktext=任务简介&userid=402881916ba10b8a016ba113adbc0006&subjectIds=402881916b9d3031016b9d626593000c,bfc5bd62010f467cbbe98c9e4741733b&departIds&groupIds=402881916b9d3031016b9d63a172000d,402881916b9d3031016b9d63d7af000e&userids=402881916ba10b8a016ba113adbc0006
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     *                                 601:任务名称不能为空</br>
     *                                 602:发布人id有误</br>
     *                                 605:机构不存在</br>
     *                                 606:部门不存在</br>
     *                                 607:组织不存在</br>
     *                                 608:用户不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"issuertime": "2018-12-12 12:12:12","sendstate": "","id": "4028d8816bcb8bc8016bcbe014240011","type": "1","title": "任务名称","tasktext": "任务简介"}
     * }
     */
    @RequiresPermissions(PERMISSION_ADD)
    @PostMapping("/add")
    private JsonResult add(String title,
                           String type,
                           String issuertime,
                           String tasktext,
                           String userid,
                           String[] subjectIds,
                           String[] departIds,
                           String[] groupIds,
                           String[] userids) {
        if (StringUtils.isBlank(title)) {
            return JsonResult.failure(601, "任务名称不能为空");
        }
        Task task = new Task();
        task.setCreatedDate(new Date());
        task.setLastModifiedDate(new Date());
        task.setTitle(title);
        task.setType(type);
        User user1 = userService.findById(userid);
        if (user1 == null) {
           return JsonResult.failure(602, "发布人id有误");
        }
        task.setUser(user1);
        task.setIssuertime(issuertime);
        task.setTasktext(tasktext);
        List<Subject> subjects = subjectService.findbyIds(subjectIds);
            if (subjects.size()!=subjectIds.length){
                return JsonResult.failure(605,"有"+(subjectIds.length-subjects.size())+"个机构不存在");
            }
        List<Depart> departs = departService.findbyIds(departIds);
            if (departs.size()!=departIds.length){
                return JsonResult.failure(606,"有"+(departIds.length-departs.size())+"个部门不存在");
            }
        List<Groups> groups = groupsService.findByIds(groupIds);
            if (groups.size()!=groupIds.length){
                return JsonResult.failure(607,"有"+(groupIds.length-groups.size())+"个组织不存在");
            }
        List<User> users = userService.findbyIds(userids);
            if (users.size()!=userids.length){
                return JsonResult.failure(608,"有"+(userids.length-users.size())+"个用户不存在");
            }
        task = taskService.save(task,subjectIds,departIds,groupIds,userids);

        jsonResult.setData(converTask(task));
        return jsonResult;
    }



    /**
     * @api {POST} /task/view 查看
     * @apiGroup Task
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /task/view?id=4028d8816bcb8bc8016bcbe014240011
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *      "data": {"issuertime": "任务简介","subjects": [{"code": "1","name": "分院党组织","id": "402881916b9d3031016b9d626593000c","subjectType": "part"},{"code": "0","name": "富阳人民医院","id": "bfc5bd62010f467cbbe98c9e4741733b","subjectType": "part"}],"workType": "1","name": "钱正","sendstatu": null,"groups": [{"code": "0","name": "行政支部","remark": "","id": "402881916b9d3031016b9d63a172000d"},{"code": "1","name": "后勤支部","remark": "","id": "402881916b9d3031016b9d63d7af000e"}],"title": "任务名称","tasktext": "任务简介","users": [{"password": "6A36E430976A64EA","salt": "45a1d914886d4a92b6835a181b2a20d8","code": "001","sex": "0","name": "钱正","remark": "暂无描述","id": "402881916ba10b8a016ba113adbc0006","userType": "normal","account": "user","status": ""}],"departs": []}
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/view")
    public JsonResult view(String id) {
        Task task = taskService.find(id);
        List<TaskInfoDepart> taskInfoDeparts = taskInfoDepartService.findByTaskId(id);
        if (task == null) {
            return jsonResult.notFound("任务不存在");
        }
        JSONObject object = new JSONObject();
        JSONArray  subjects = new JSONArray();
        JSONArray  departs = new JSONArray();
        JSONArray  groups = new JSONArray();
        JSONArray  users = new JSONArray();
        object.put("id",task.getId());
        object.put("title", task.getTitle());
        object.put("workType", task.getType());
        object.put("issuertime", task.getTasktext());
        object.put("name", task.getUser().getName());
        object.put("tasktext", task.getTasktext());
        object.put("sendstatu", task.getSendstate());

        for(TaskInfoDepart taskInfoDepart: taskInfoDeparts){
            if(taskInfoDepart.getSubject()!= null){
                JSONObject json = new JSONObject(JsonUtils.getJson(taskInfoDepart.getSubject()));
                subjects.add(json);
            }
            if (taskInfoDepart.getDepart()!=null){
                JSONObject json = new JSONObject(JsonUtils.getJson(taskInfoDepart.getDepart()));
                departs.add(json);
            }
            if(taskInfoDepart.getGroups()!=null){
                JSONObject json = new JSONObject(JsonUtils.getJson(taskInfoDepart.getGroups()));
                groups.add(json);
            }
            if(taskInfoDepart.getUser()!=null){
                JSONObject json = new JSONObject(JsonUtils.getJson(taskInfoDepart.getUser()));
                users.add(json);
            }
        }
        object.put("subjects", subjects);
        object.put("departs", departs);
        object.put("groups", groups);
        object.put("users", users);
        jsonResult.setData(object);
        return jsonResult;

    }


    /**
     * @api {POST} /task/del 删除
     * @apiGroup Task
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /task/del?id=4028d8816bcc2280016bcc271c420000
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:活动不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": []
     * }
     */
    @RequiresPermissions(PERMISSION_DELETE)
    @PostMapping("/del")
    public JsonResult del(String id) {
        Task task = taskService.find(id);
        taskInfoDepartService.deleteTask(task);
        return JsonResult.success();
    }


    /**
     * @api {POST} /task/findBydeption 根据部门组织机构查找任务
     * @apiGroup Task
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /task/findBydeption?id=402881916b9d3031016b9d63a172000d
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *       "data": [{"issuertime": "2018-12-12 12:12:12","name": "钱正","sendstate": "未发送任务","id": "4028d8816bcb8bc8016bcbe014240011","title": "任务名称","type": "1","tasktext": "任务简介"}]
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/findBydeption")
    public JsonResult findBydeption(String id) {
        List<Task> tasksss = taskService.findAdvicesByDeptionId(id);
        JSONArray arr = new JSONArray();
        JSONObject object;
        for (Task task : tasksss) {
            object = new JSONObject();
            object.put("id", task.getId());
            object.put("title", task.getTitle());
            object.put("name", task.getUser().getName());
            object.put("type", task.getType());
            object.put("issuertime", task.getIssuertime());
            object.put("tasktext", task.getTasktext());
            if (task.getSendstate() == null) {
                task.setSendstate("未发送任务");
            }
            object.put("sendstate", task.getSendstate());
            arr.add(object);
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * @api {POST} /task/sendTask 发送任务
     * @apiGroup Task
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 必填 任务id
     * @apiParamExample {json} 请求样例:
     *                   /task/sendTask?id=4028d8816bcb8bc8016bcbe014240011
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                  601:已经发送过任务<br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     *  "data": []
     * }
     */
    @PostMapping("/sendTask")
    public JsonResult sendTask(String id){
         List<TaskInfoUser> taskInfoUsers = taskService.findsentistrue(id);
         if(CollectionUtils.isNotEmpty(taskInfoUsers)){
             return JsonResult.failure(601,"已经发送过此任务");
         }
        List<TaskInfoDepart> taskInfoDeparts = taskInfoDepartService.findByTaskId(id);
        if (CollectionUtils.isEmpty(taskInfoDeparts)){
            return JsonResult.notFound("没有找到此任务下的用户");
        }
        //发送任务返回用戶实体
        List<User> userList = taskService.sendtask(taskInfoDeparts);
        //通过实体找到电话号码
//        Map<String,String> map = taskService.findphoneByUser(userList);
//        String url = "http://localhost:8080/duanxin/sendSMSSS";
//        String result = HttpUtils.sendPost(url, map);
        String state ="已发送";
        taskService.updatstate(id,state);
        return jsonResult;
    }



    /**
     *
     * 转换Task为json
     */
    private JSONObject converTask(Task task) {
        JSONObject json = new JSONObject(JsonUtils.getJson(task));
        return json;
    }


}
