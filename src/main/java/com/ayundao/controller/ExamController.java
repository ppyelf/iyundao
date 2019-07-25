package com.ayundao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.ayundao.base.BaseController.*;

/**
 * @ClassName: ExamController
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/6
 * @Description: 实现 - 考试
 * @Version: V1.0
 */
@RequiresUser
@RequiresRoles(value = {ROLE_ADMIN, ROLE_USER, ROLE_MANAGER, ROLE_PUBLISHER, ROLE_AUDITOR}, logical = Logical.OR)
@RestController
@RequestMapping("/exam")
public class ExamController extends BaseController{

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamInfoDepartService examInfoDepartService;

    @Autowired
    private ExamInfoTextpaperService examInfoTextpaperService;

    @Autowired
    private ExamInfoUserService examInfoUserService;

    @Autowired
    private ExamInfoUserScoreService examInfoUserScoreService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private DepartService departService;

    @Autowired
    private TestpaperService testpaperService;
    /**
     * @api {GET} /exam/list 考试列表
     * @apiGroup Exam
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求样例:
     *                /exam/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{"examlong": "60","starttime": "2121","title": "name","score": "100","showthat": "员工测试","overtime": "2121","id": "123123"},{"examlong": "60","starttime": "2121","title": "name","score": "100","showthat": "员工测试","overtime": "2121","id": "55555555"}]
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @GetMapping("/list")
    public JsonResult list(){
        List<Exam> exams = examService.findAll();
        JSONArray arr = new JSONArray();
         for (Exam exam : exams){
                arr.add(converExam(exam));
         }
        jsonResult.setData(arr);

        return jsonResult;
    }

    /**
     * @api {POST} /exam/page 考试列表分页
     * @apiGroup Exam
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {int} page 跳过的页数 默认0
     * @apiParam {int} size 必填 每页的条数 默认10
     * @apiParamExample {json} 请求样例:
     *                /exam/page?=1&size=4
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *      "data": {"total": 5,"totalPage": 1,"page": 0,"content": [{"examlong": "60","score": "100","showthat": "简介","overtime": "2018-12-12 12:12:12","id": "4028d8816bded8a8016bdee853d8000c","starttime": "2018-12-12 12:12:12","title": "学院检测"}]}
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/page")
    public  JsonResult page(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size){
        Page<Exam> examPage = examService.finALLForPPage(new Pageable(page,size));
        jsonResult.setData(JsonUtils.getPage(examPage));
        return jsonResult;
    }


    /**
     * @api {POST} /exam/searchByname 模糊查询考试标题分页
     * @apiGroup Exam
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} title 考试名称（标题） 
     * @apiParam {int} page 跳过的页数 默认0
     * @apiParam {int} size 必填 每页的条数 默认10
     * @apiParamExample {json} 请求样例:
     *                /exam/searchByname?title=学
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *        "data": {
                    "total": 4,             z
                    "totalPage": 1,
                    "page": 0,
                    "content": [
    {
                            "examlong": "60",                                           考试时长
                            "score": "100",                                             合格分数
                            "showthat": "简介",                                       考试说明
                            "overtime": "2018-12-12 12:12:12",                          开始结束时间
                            "id": "4028d8816bcb8bc8016bcb8de2b40008",                       考试id
                            "starttime": "2018-12-12 12:12:12",                         考试开始时间
                            "title": "学院检测"                                              标题
    },
    {
                            "examlong": "60",
                            "score": "100",
                            "showthat": "简介",
                            "overtime": "2018-12-12 12:12:12",
                            "id": "4028d8816bcb8bc8016bcbc8d9910010",
                            "starttime": "2018-12-12 12:12:12",
                            "title": "学院检测"
    },]
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/searchByname")
    public JsonResult searchByname(String title,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size){
        String name  = "title";
        Pageable pageable = new Pageable(page,size);
        pageable.setSearchProperty(name);
        pageable.setSearchValue(title);
        Page<Exam> examPage = examService.findNameForPage(pageable);
        jsonResult.setData(JsonUtils.getPage(examPage));
        return jsonResult;
    }



    /**
     * @api {POST} /exam/add 新增
     * @apiGroup Exam
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} title 必填 标题
     * @apiParam {String} starttime 考试开始时间
     * @apiParam {String} overtime 开始结束时间
     * @apiParam {String} examlong 考试时长
     * @apiParam {String} score 合格分数
     * @apiParam {String} showthat 考试说明
     * @apiParam {String[]} subjectIds 机构id
     * @apiParam {String[]} departIds 部门id
     * @apiParam {String[]} groupIds    组织id
     * @apiParam {String[]} userids 用户id
     * @apiParam {String[]} testpapers  试卷id
     * @apiParamExample {json} 请求样例:
     *                exam/add?title=学院检测&starttime=2018-12-12 12:12:12&overtime=2018-12-12 12:12:12&examlong=60&score=100&showthat=简介&subjectIds=402881916b9d3031016b9d626593000c,bfc5bd62010f467cbbe98c9e4741733b&departIds&groupIds=402881916b9d3031016b9d63a172000d,402881916b9d3031016b9d63d7af000e&userids=402881916ba10b8a016ba113adbc0006&testpapers=1,2
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     *                                 601:考试标题不能为空<br>
     *                                 605:机构不存在</br>
     *                                 606:部门不存在</br>
     *                                 607:组织不存在</br>
     *                                 608:用户不存在</br>
     *                                 609:试卷不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"examlong": "60","score": "100","showthat": "简介","overtime": "2018-12-12 12:12:12","id": "4028d8816bcb8bc8016bcb8de2b40008","starttime": "2018-12-12 12:12:12","title": "学院检测"}
     * }
     */
    @RequiresPermissions(PERMISSION_ADD)
    @PostMapping("/add")
    public JsonResult add(String title,
                          String starttime,
                          String overtime,
                          String examlong,
                          String score,
                          String showthat,
                          String[] subjectIds,
                          String[] departIds,
                          String[] groupIds,
                          String[] testpapers,
                          String[] userids
                          ){
        if(StringUtils.isBlank(title)){
            return JsonResult.failure(601,"考试标题不能为空");
        }
        Exam exam = new Exam();
        exam.setCreatedDate(new Date());
        exam.setLastModifiedDate(new Date());
        exam.setTitle(title);
        exam.setStarttime(starttime);
        exam.setOvertime(overtime);
        exam.setExamlong(examlong);
        exam.setScore(score);
        exam.setShowthat(showthat);
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
        List<Testpaper> testpapers1 =testpaperService.findByIds(testpapers);
            if (testpapers1.size()!=testpapers.length){
                return JsonResult.failure(609,"有"+(testpapers.length-testpapers1.size())+"个用户不存在");
            }
        List<User> users = userService.findbyIds(userids);
            if (users.size()!=userids.length){
                return JsonResult.failure(608,"有"+(userids.length-users.size())+"个用户不存在");
            }
        exam = examService.save(exam,subjectIds,departIds,groupIds,testpapers,userids);
        jsonResult.setData(converExam(exam));
        return jsonResult;
    }

    /**
     * @api {POST} /exam/view 查看考试
     * @apiGroup Exam
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *                /exam/view?id=4028d8816bcc9a32016bcccd9616000c
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *      "data": {"examlong": "60","score": "100","textpapers": [{"name": "第一张","id": "1"},{"name": "第二张","id": "2"}],"subjects": [{"code": "1","name": "分院党组织","id": "402881916b9d3031016b9d626593000c","subjectType": "part"},{"code": "0","name": "富阳人民医院","id": "bfc5bd62010f467cbbe98c9e4741733b","subjectType": "part"}],"showthat": "简介","overtime": "2018-12-12 12:12:12","groups": [{"code": "0","name": "行政支部","remark": "","id": "402881916b9d3031016b9d63a172000d"},{"code": "1","name": "后勤支部","remark": "","id": "402881916b9d3031016b9d63d7af000e"}],"starttime": "2018-12-12 12:12:12","title": "学院检测","users": [{"password": "6A36E430976A64EA","code": "001","salt": "45a1d914886d4a92b6835a181b2a20d8","sex": "0","name": "钱正","remark": "暂无描述","id": "402881916ba10b8a016ba113adbc0006","userType": "normal","account": "user","status": ""}],"departs": []}
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/view")
    public JsonResult view(String id){
        Exam exam = examService.findById(id);
        if (exam == null){
            return jsonResult.notFound("考试不存在");
        }
        List<ExamInfoDepart> examInfoDeparts = examInfoDepartService.findByExamId(id);
        List<ExamInfoTextpaper> examInfoTextpapers = examInfoTextpaperService.findByExamId(id);
        JSONObject object = examService.show(exam,examInfoDeparts,examInfoTextpapers);
        jsonResult.setData(object);
        return jsonResult;
    }


    /**
     * @api {POST} /exam/del 删除
     * @apiGroup Exam
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *               /exam/del?id=4028d8816bcf68cb016bcf7e22810008
     * @apiSuccess (200) {String} code 200:成功</br>
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
    public JsonResult del(String id){
        Exam exam = examService.findById(id);
        if (exam ==null){
           return JsonResult.notFound("考试不存在");
        }
        examService.deleteExam(exam);
        return  JsonResult.success();
    }


    /**
     * @api {POST} /exam/score 考试得分列表
     * @apiGroup Exam
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} 考试id 必填
     * @apiParamExample {json} 请求样例:
     *                   /exam/score?id=4028d8816bcb8bc8016bcb8de2b40008
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 200,
     * "message": "成功",
     *  "data": [{"score": "10","name": "钱正"}]
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/score")
    public JsonResult score(String id){
        Exam exam = examService.findById(id);
        if (exam ==null){
            return JsonResult.notFound("考试不存在");
        }
        JSONArray arr = new JSONArray();

        List<ExamInfoUserScore> examInfoUserScores = examService.findALLScore(exam);
        if (CollectionUtils.isNotEmpty(examInfoUserScores)){
            for (ExamInfoUserScore examInfoUserScore : examInfoUserScores) {
                JSONObject object = new JSONObject();
                object.put("name",examInfoUserScore.getUser().getName());
                object.put("score",examInfoUserScore.getScore());
                arr.add(object);
            }
        }
        jsonResult.setData(arr);
        return jsonResult;

    }

    /**
     * @api {POST} /exam/addExamInfoUser 新增个人考试情况
     * @apiGroup Exam
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} examid 考试id必填
     * @apiParam {String} testpaperid 试卷id必填
     * @apiParam {String} userid  用户id必填
     * @apiParam {String[]} changge  请看样例
     * @apiParamExample {json} 请求样例:
     *                {
                        "examid":"4028d8816bcb8bc8016bcb8de2b40008",
                        "testpaperid":"4028d8816bd07269016bd0c82ac10027",
                        "userid":"402881916ba10b8a016ba113adbc0006",
                        "changge":[{"paperTitleid":"4028d8816bd07269016bd0c82ac10028","answerselect":"1.1"},{"paperTitleid":"4028d8816bd07269016bd0c82ac6002c","answerselect":"2.3"},{"paperTitleid":"4028d8816bd07269016bd0c82ac80030","answerselect":"3.1"}]}
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     *                                 601:任务名称不能为空</br>
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
    @PostMapping("/addExamInfoUser")
    public JsonResult addExamInfoUser(@RequestBody JSONObject params){
        JSONObject object = params;
      String examid = (String)params.get("examid");
      String testpaperid =  (String)params.get("testpaperid");
       String userid =  (String)params.get("userid");
        List<Map<String,String>> mapList= (List<Map<String,String>>) params.get("changge");

        jsonResult =examService.saveExamAndUser(examid,testpaperid,userid,mapList);
        return jsonResult;
    }

    /**
     * @api {POST} /exam/findExamInfoUser 查看个人考试情况
     * @apiGroup Exam
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} id 必填
     * @apiParamExample {json} 请求样例:
     *               /exam/findExamInfoUser?examid=4028d8816bcb8bc8016bcb8de2b40008&userid=402881916ba10b8a016ba113adbc0006
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"examtitle": "学院检测","test": [{"answer": "1.1","papertitle": "111","istrue": {}},{"answer": "2.3","papertitle": "222","istrue": {}},{"answer": "3.1","papertitle": "333","istrue": {}}],"username": "钱正"}
     * }
     */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/findExamInfoUser")
    public JsonResult findExamInfoUser(String examid,String userid){
        List<ExamInfoUser> examInfoUsers = examInfoUserService.findByExamUserId(examid,userid);
        ExamInfoUserScore examInfoUserScore = examInfoUserScoreService.findByExamUserId(examid,userid);
        if(CollectionUtils.isEmpty(examInfoUsers)){
            return JsonResult.notFound("没有这个记录");
        }
        if(examInfoUserScore ==null){
            return JsonResult.notFound("找不到分数");
        }
        JSONObject object = new JSONObject();
        object.put("examtitle",examInfoUserScore.getExam().getTitle());
        object.put("username",examInfoUserScore.getUser().getName());

        JSONArray arr = new JSONArray();
        for (ExamInfoUser examInfoUser : examInfoUsers) {
            JSONObject obj = new JSONObject();
            obj.put("papertitle",examInfoUser.getPaperTitle().getExamcontent());
            obj.put("answer",examInfoUser.getAnswerselect());
            obj.put("istrue",examInfoUser.getAnswer());
            arr.add(obj);
        }
        object.put("test",arr);
        jsonResult.setData(object);
        return  jsonResult;
    }

    /**
    * @api {POST} /exam/socreforuser 查看个人考试得分记录
    * @apiGroup Exam
    * @apiVersion 1.0.0
    * @apiDescription 查看
     * @apiHeader {String} IYunDao-AssessToken token验证
    * @apiParam {String} 用户id必填
    * @apiParamExample {json} 请求样例:
    *                /exam/socreforuser?id=4028d8816bcc9a32016bcccd9616000c
    * @apiSuccess (200) {String} code 200:成功</br>
    * @apiSuccess (200) {String} message 信息
    * @apiSuccess (200) {String} data 返回用户信息
    * @apiSuccessExample {json} 返回样例:
    * {
    *     "code": 200,
    *     "message": "成功",
    *      "data": [
    {
    "score": "10",                                  得分
    "examtitle": "学院检测",                        考试名称
    "examid": "4028d8816bcb8bc8016bcb8de2b40008",   考试id
    "id": "4028d8816bd49aee016bd4a2edf50013",       这条记录的id
    "userid": "402881916ba10b8a016ba113adbc0006",   用户id
    "username": "钱正"                                用户名称
    },]
    * }
    */
    @RequiresPermissions(PERMISSION_VIEW)
    @PostMapping("/socreforuser")
    public JsonResult socreforuser(String id){
        User user = userService.findById(id);
        if(user ==null){
            return  JsonResult.notFound("找不到用户");
        }
        List<ExamInfoUserScore> examInfoUserScore = examService.findScoreByUser(user);
        JSONArray arr = new JSONArray();
        System.out.println("sadasde"+examInfoUserScore);
        if (CollectionUtils.isNotEmpty(examInfoUserScore)){
            for (ExamInfoUserScore infoUserScore : examInfoUserScore) {
                JSONObject object = new JSONObject();

                object.put("id",infoUserScore.getId());
                object.put("score",infoUserScore.getScore());
                object.put("examid",infoUserScore.getExam().getId());
                object.put("examtitle",infoUserScore.getExam().getTitle());
                object.put("userid",infoUserScore.getUser().getId());
                object.put("username",infoUserScore.getUser().getName());
                arr.add(object);
            }
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    /**
     * 转换Exam 为json
     * @param exam
     * @return
     */
    private JSONObject converExam(Exam exam){
            JSONObject jsonObject = new JSONObject(JsonUtils.getJson(exam));
            return jsonObject;

    }


}
