package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.utils.ExcelUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.*;
import com.ayundao.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: IdeologyController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 5:35
 * @Description: 控制层 - 意识形态
 * @Version: V1.0
 */
@RestController
@RequestMapping("/ideology")
public class IdeologyController extends BaseController {

    @Autowired
    private IdeologyService ideologyService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DepartService departService;

    @Autowired
    private GroupsService groupsService;

    /**
     * @api {POST} /ideology/downloadCountry 下载学习强国模板
     * @apiGroup Ideology
     * @apiVersion 1.0.0
     * @apiDescription 下载模板
     * @apiParamExample {json} 请求示例:
     *                  /ideology/downloadCountry
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
    @GetMapping("/downloadCountry")
    public JsonResult downloadCountry(HttpServletResponse resp) {
        try {
            ExcelUtils.downloadWorkBook("学习强国", CountryRank.class, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResult.success();
    }

    /**
     * @api {POST} /ideology/importCountry 导入学习强国
     * @apiGroup Ideology
     * @apiVersion 1.0.0
     * @apiDescription 下载模板
     * @apiParamExample {json} 请求示例:
     *                  /ideology/importCountry
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "score": 100,    "subject": {        "name": "测试添加",        "id": "402881916b726258016b7298a5bf0006",        "subjectType": "etc"    },    "name": "张三",    "user": {        "password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06",        "salt": "3a10624a300f4670",        "sex": "0",        "name": "管理员",        "remark": "未填写",        "id": "0a4179fc06cb49e3ac0db7bcc8cf0882",        "userType": "admin",        "account": "admin",        "status": "normal"    }},{    "score": 90,    "subject": {        "name": "测试添加机构",        "id": "402881916b726258016b729938b30007",        "subjectType": "head"    },    "name": "李四",    "user": {        "password": "6460A880493E3011",        "salt": "8c204e8c3e7b4393",        "sex": "0",        "name": "测试账号",        "remark": "",        "id": "402881916b5a1eba016b5a1f33d60000",        "userType": "admin",        "account": "123",        "status": ""    }},{    "score": 80,    "subject": {        "name": "1",        "id": "402881916b73f5df016b74339bc20001",        "subjectType": "part"    },    "name": "王二",    "user": {        "password": "6DC46CD207921D73",        "salt": "e174ea76af1b462a",        "sex": "0",        "name": "测试账号11",        "remark": "",        "id": "402881916b5a1eba016b5a1fafc70002",        "userType": "admin",        "account": "1232",        "status": ""    }},{    "score": 70,    "subject": {        "name": "修改机构",        "id": "402881f46afdef14016afe28796c000b",        "subjectType": "etc"    },    "name": "测试",    "user": {        "password": "B07EEB8D7F62FBD7",        "salt": "45a1d914886d4a92b6835a181b2a20d8",        "sex": "0",        "name": "测试账号11",        "remark": "",        "id": "402881916b737ac1016b73b07131000b",        "userType": "admin",        "account": "12345",        "status": ""    }}
     *     ]
     * }
     */
    @PostMapping("/importCountry")
    public JsonResult importCountry(MultipartFile file) {
        List<CountryRank> list = ExcelUtils.readExcel(file, CountryRank.class);
        list = ideologyService.saveAllCountry(list);
        jsonResult.setData(convertCountry(list));
        return jsonResult;
    }

    /**
     * @api {POST} /ideology/countryList 强国排名
     * @apiGroup Ideology
     * @apiVersion 1.0.0
     * @apiDescription 下载模板
     * @apiParam {int} type (0-默认,本日;1-本周;2-本月;3-本年)
     * @apiParamExample {json} 请求示例:
     *                  ?type=0
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "score": 100,    "subject": {        "name": "测试添加",        "id": "402881916b726258016b7298a5bf0006",        "subjectType": "etc"    },    "name": "张三",    "user": {        "password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06",        "salt": "3a10624a300f4670",        "sex": "0",        "name": "管理员",        "remark": "未填写",        "id": "0a4179fc06cb49e3ac0db7bcc8cf0882",        "userType": "admin",        "account": "admin",        "status": "normal"    }},{    "score": 90,    "subject": {        "name": "测试添加机构",        "id": "402881916b726258016b729938b30007",        "subjectType": "head"    },    "name": "李四",    "user": {        "password": "6460A880493E3011",        "salt": "8c204e8c3e7b4393",        "sex": "0",        "name": "测试账号",        "remark": "",        "id": "402881916b5a1eba016b5a1f33d60000",        "userType": "admin",        "account": "123",        "status": ""    }},{    "score": 80,    "subject": {        "name": "1",        "id": "402881916b73f5df016b74339bc20001",        "subjectType": "part"    },    "name": "王二",    "user": {        "password": "6DC46CD207921D73",        "salt": "e174ea76af1b462a",        "sex": "0",        "name": "测试账号11",        "remark": "",        "id": "402881916b5a1eba016b5a1fafc70002",        "userType": "admin",        "account": "1232",        "status": ""    }},{    "score": 70,    "subject": {        "name": "修改机构",        "id": "402881f46afdef14016afe28796c000b",        "subjectType": "etc"    },    "name": "测试",    "user": {        "password": "B07EEB8D7F62FBD7",        "salt": "45a1d914886d4a92b6835a181b2a20d8",        "sex": "0",        "name": "测试账号11",        "remark": "",        "id": "402881916b737ac1016b73b07131000b",        "userType": "admin",        "account": "12345",        "status": ""    }}
     *     ]
     * }
     */
    @PostMapping("/countryList")
    public JsonResult countryList(@RequestParam(defaultValue = "0") int type) {
        List<CountryRank> list = ideologyService.findCountryOrderByCreatedTime(type);
        jsonResult.setData(convertCountry(list));
        return jsonResult;
    }

    /**
     * @api {POST} /ideology/downloadPioneer 下载先锋人物模板
     * @apiGroup Ideology
     * @apiVersion 1.0.0
     * @apiDescription 下载模板
     * @apiParamExample {json} 请求示例:
     *                          /ideology/downloadPioneer
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
    @GetMapping("/downloadPioneer")
    public JsonResult downloadPioneer(HttpServletResponse resp) {
        try {
            ExcelUtils.downloadWorkBook("先锋人物", Pioneer.class, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResult.success();
    }

    /**
     * @api {POST} /ideology/importPioneer 导入先锋人物
     * @apiGroup Ideology
     * @apiVersion 1.0.0
     * @apiDescription 下载模板
     * @apiParamExample {json} 请求示例:
     *                   /ideology/importPioneer
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "score": 100,    "subject": {        "name": "测试添加",        "id": "402881916b726258016b7298a5bf0006",        "subjectType": "etc"    },    "name": "张三",    "user": {        "password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06",        "salt": "3a10624a300f4670",        "sex": "0",        "name": "管理员",        "remark": "未填写",        "id": "0a4179fc06cb49e3ac0db7bcc8cf0882",        "userType": "admin",        "account": "admin",        "status": "normal"    }},{    "score": 90,    "subject": {        "name": "测试添加机构",        "id": "402881916b726258016b729938b30007",        "subjectType": "head"    },    "name": "李四",    "user": {        "password": "6460A880493E3011",        "salt": "8c204e8c3e7b4393",        "sex": "0",        "name": "测试账号",        "remark": "",        "id": "402881916b5a1eba016b5a1f33d60000",        "userType": "admin",        "account": "123",        "status": ""    }},{    "score": 80,    "subject": {        "name": "1",        "id": "402881916b73f5df016b74339bc20001",        "subjectType": "part"    },    "name": "王二",    "user": {        "password": "6DC46CD207921D73",        "salt": "e174ea76af1b462a",        "sex": "0",        "name": "测试账号11",        "remark": "",        "id": "402881916b5a1eba016b5a1fafc70002",        "userType": "admin",        "account": "1232",        "status": ""    }},{    "score": 70,    "subject": {        "name": "修改机构",        "id": "402881f46afdef14016afe28796c000b",        "subjectType": "etc"    },    "name": "测试",    "user": {        "password": "B07EEB8D7F62FBD7",        "salt": "45a1d914886d4a92b6835a181b2a20d8",        "sex": "0",        "name": "测试账号11",        "remark": "",        "id": "402881916b737ac1016b73b07131000b",        "userType": "admin",        "account": "12345",        "status": ""    }}
     *     ]
     * }
     */
    @PostMapping("/importPioneer")
    public JsonResult importPioneer(MultipartFile file) {
        List<Pioneer> list = ExcelUtils.readExcel(file, Pioneer.class);
        list = ideologyService.saveAllPioneer(list);
        jsonResult.setData(convertPioneer(list));
        return jsonResult;
    }

    /**
     * @api {POST} /ideology/pioneerList 先锋人物排名
     * @apiGroup Ideology
     * @apiVersion 1.0.0
     * @apiDescription 下载模板
     * @apiParam {int} type (0-默认,本日;1-本周;2-本月;3-本年)
     * @apiParamExample {json} 请求示例:
     *                  ?type=0
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "score": 100,    "subject": {        "name": "测试添加",        "id": "402881916b726258016b7298a5bf0006",        "subjectType": "etc"    },    "name": "张三",    "user": {        "password": "b356a1a11a067620275401a5a3de04300bf0c47267071e06",        "salt": "3a10624a300f4670",        "sex": "0",        "name": "管理员",        "remark": "未填写",        "id": "0a4179fc06cb49e3ac0db7bcc8cf0882",        "userType": "admin",        "account": "admin",        "status": "normal"    }},{    "score": 90,    "subject": {        "name": "测试添加机构",        "id": "402881916b726258016b729938b30007",        "subjectType": "head"    },    "name": "李四",    "user": {        "password": "6460A880493E3011",        "salt": "8c204e8c3e7b4393",        "sex": "0",        "name": "测试账号",        "remark": "",        "id": "402881916b5a1eba016b5a1f33d60000",        "userType": "admin",        "account": "123",        "status": ""    }},{    "score": 80,    "subject": {        "name": "1",        "id": "402881916b73f5df016b74339bc20001",        "subjectType": "part"    },    "name": "王二",    "user": {        "password": "6DC46CD207921D73",        "salt": "e174ea76af1b462a",        "sex": "0",        "name": "测试账号11",        "remark": "",        "id": "402881916b5a1eba016b5a1fafc70002",        "userType": "admin",        "account": "1232",        "status": ""    }},{    "score": 70,    "subject": {        "name": "修改机构",        "id": "402881f46afdef14016afe28796c000b",        "subjectType": "etc"    },    "name": "测试",    "user": {        "password": "B07EEB8D7F62FBD7",        "salt": "45a1d914886d4a92b6835a181b2a20d8",        "sex": "0",        "name": "测试账号11",        "remark": "",        "id": "402881916b737ac1016b73b07131000b",        "userType": "admin",        "account": "12345",        "status": ""    }}
     *     ]
     * }
     */
    @PostMapping("/pioneerList")
    public JsonResult pioneerList(@RequestParam(defaultValue = "0") int type) {
        List<Pioneer> list = ideologyService.findPioneerOrderByCreatedTime(type);
        jsonResult.setData(convertPioneer(list));
        return jsonResult;
    }

    /**
     * 转换强国排名信息
     * @param list
     * @return
     */
    private JSONArray convertPioneer(List<Pioneer> list) {
        JSONArray arr = new JSONArray();
        for (Pioneer pioneer : list) {
            JSONObject json = new JSONObject();
            json.put("name", pioneer.getName());
            json.put("score", pioneer.getScore());
            //todo 不需要更改为编号
            Subject subject = subjectService.find(pioneer.getSubjectCode());
            Depart depart = departService.findById(pioneer.getDepartCode());
            Groups groups = groupsService.findById(pioneer.getGroupCode());
            User user = userService.findById(pioneer.getCode());
            json.put("subject", JsonUtils.getJson(subject));
            json.put("user", JsonUtils.getJson(user));
            if (depart != null) {
                json.put("depart", JsonUtils.getJson(depart));
            }
            if (groups != null) {
                json.put("groups", JsonUtils.getJson(groups));
            }
            arr.add(json);
        }
        return arr;
    }


    /**
     * 转换先锋人物排名信息
     * @param list
     * @return
     */
    private JSONArray convertCountry(List<CountryRank> list) {
        JSONArray arr = new JSONArray();
        for (CountryRank cr : list) {
            JSONObject json = new JSONObject();
            json.put("name", cr.getName());
            json.put("score", cr.getScore());
            //todo 不需要更改为编号
            Subject subject = subjectService.find(cr.getSubjectId());
            Depart depart = departService.findById(cr.getDepartId());
            Groups groups = groupsService.findById(cr.getGroupId());
            User user = userService.findById(cr.getUserId());
            json.put("subject", JsonUtils.getJson(subject));
            json.put("user", JsonUtils.getJson(user));
            if (depart != null) {
                json.put("depart", JsonUtils.getJson(depart));
            }
            if (groups != null) {
                json.put("groups", JsonUtils.getJson(groups));
            }
            arr.add(json);
        }
        return arr;
    }
}
