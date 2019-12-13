package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.BaseRepository;
import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.service.DrugsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

/**
 * @ClassName: DrugsController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/23 11:53
 * @Description: 控制层 - 药品预警
 * @Version: V1.0
 */
@RestController
@RequestMapping("/drugs")
public class DrugsController extends BaseController {

    private static final long serialVersionUID = -127398127398219738L;

    @Autowired
    private DrugsService drugsService;

    /**
     * @api {GET} /drugs/download 下载药品预警模板
     * @apiGroup Drugs
     * @apiVersion 1.0.0
     * @apiDescription 下载药品预警模板
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParamExample {json} 请求示例:
     * /evaluation/download
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *      "code": 200,
     *      "message": "成功",
     *      "data": {""}
     * }
     */
    @GetMapping("/download")
    public JsonResult downloadDrugs(HttpServletRequest req, HttpServletResponse resp) {
        try {
            FileUtils.download(ResourceUtils.getFile("classpath:static/excel/药品.xls").getAbsolutePath(), req, resp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return JsonResult.success();
    }

    /**
     * @api {POST} /drugs/upload 导入药品预警
     * @apiGroup Drugs
     * @apiVersion 1.0.0
     * @apiDescription 导入药品预警
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {MultipartFile} file 上传文件,必填
     * @apiParamExample {json} 请求示例:
     * /evaluation/upload
     * @apiSuccess (200) {String} code 200:成功</br>
     * 601:导入到文件不能为空</br>
     * 602:第1行中医院不存在,请输入正确的机构名称</br>
     * 603:第2行情输入正确的日期格式,如:2019年09月药品使用预警（医生约谈）签到表</br>
     * 604:第2行胸牌号或姓名为空,查询不存在</br>
     * 606:第2行的备注超过100个字符</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [{    "subject": {        "code": "0",        "name": "富阳区第一人民医院"    },    "name": "",    "remark": "1",    "id": "402881916e971509016e972091430016",    "time": "201909",    "user": {        "code": "a325",        "name": "宋桂莲"    }}
     *     ]
     * }
     */
    @PostMapping("/upload")
    public JsonResult uploadDrugs(MultipartFile file) {
        if (file ==null || file.isEmpty()) {
            return JsonResult.failure(601, "导入到文件不能为空");
        }
        return drugsService.upload(file, jsonResult);
    }

    /**
     * @api {POST} /drugs/list 分页
     * @apiGroup Drugs
     * @apiVersion 1.0.0
     * @apiDescription 分页
     * @apiHeader {String} IYunDao-AssessToken token验证
     * @apiParam {String} time 时间
     * @apiParam {String} code 胸牌号
     * @apiParam {int} num 页码,默认0
     * @apiParam {int} size 页宽,默认10
     * @apiParamExample {json} 请求示例:
     * /evaluation/list
     * @apiSuccess (200) {String} code 200:成功</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {"content": [    {        "USERNAME": "宋桂莲",        "YEAR": "201909",        "VERSION": 0,        "SUBJECTNAME": "富阳区第一人民医院",        "CREATEDATE": "20191123151844",        "USERCODE": "a325",        "REMARK": "1",        "ID": "402881916e971509016e972091430016",        "LASTMODIFIEDTIME": "20191123151844",        "SUBJECTCODE": "0",        "NAME": ""    },    {        "USERNAME": "吕祥淑",        "ID": "402881916e971509016e9720914a0017",        "YEAR": "201909",        "VERSION": 0,        "SUBJECTNAME": "富阳区第一人民医院",        "USERCODE": "a302",        "CREATEDATE": "20191123151844",        "LASTMODIFIEDTIME": "20191123151844",        "SUBJECTCODE": "0",        "NAME": "",        "REMARK": "2"    },    {        "ID": "402881916e971509016e972091500018",        "YEAR": "201909",        "VERSION": 0,        "USERCODE": "a139",        "SUBJECTNAME": "富阳区第一人民医院",        "CREATEDATE": "20191123151844",        "USERNAME": "夏群峰",        "REMARK": "3",        "LASTMODIFIEDTIME": "20191123151844",        "SUBJECTCODE": "0",        "NAME": ""    },    {        "USERCODE": "a21",        "YEAR": "201909",        "ID": "402881916e971509016e972091540019",        "VERSION": 0,        "SUBJECTNAME": "富阳区第一人民医院",        "REMARK": "4",        "CREATEDATE": "20191123151844",        "USERNAME": "陈萍",        "LASTMODIFIEDTIME": "20191123151844",        "SUBJECTCODE": "0",        "NAME": ""    },    {        "YEAR": "201909",        "VERSION": 0,        "USERNAME": "方红梅",        "SUBJECTNAME": "富阳区第一人民医院",        "REMARK": "5",        "USERCODE": "a214",        "CREATEDATE": "20191123151844",        "ID": "402881916e971509016e9720915f001a",        "LASTMODIFIEDTIME": "20191123151844",        "SUBJECTCODE": "0",        "NAME": ""    },    {        "YEAR": "201909",        "VERSION": 0,        "SUBJECTNAME": "富阳区第一人民医院",        "CREATEDATE": "20191123151844",        "USERCODE": "a90",        "REMARK": "6",        "ID": "402881916e971509016e97209163001b",        "LASTMODIFIEDTIME": "20191123151844",        "USERNAME": "张建英",        "SUBJECTCODE": "0",        "NAME": ""    },    {        "USERNAME": "夏加英",        "ID": "402881916e971509016e9720916a001c",        "YEAR": "201909",        "VERSION": 0,        "SUBJECTNAME": "富阳区第一人民医院",        "REMARK": "7",        "CREATEDATE": "20191123151844",        "LASTMODIFIEDTIME": "20191123151844",        "USERCODE": "a178",        "SUBJECTCODE": "0",        "NAME": ""    },    {        "USERCODE": "a330",        "ID": "402881916e971509016e9720916e001d",        "REMARK": "8",        "USERNAME": "舒兴土",        "YEAR": "201909",        "VERSION": 0,        "SUBJECTNAME": "富阳区第一人民医院",        "CREATEDATE": "20191123151844",        "LASTMODIFIEDTIME": "20191123151844",        "SUBJECTCODE": "0",        "NAME": ""    },    {        "REMARK": "9",        "YEAR": "201909",        "ID": "402881916e971509016e97209174001e",        "VERSION": 0,        "SUBJECTNAME": "富阳区第一人民医院",        "USERCODE": "a225",        "CREATEDATE": "20191123151844",        "USERNAME": "张亮",        "LASTMODIFIEDTIME": "20191123151844",        "SUBJECTCODE": "0",        "NAME": ""    },    {        "REMARK": "10",        "USERCODE": "a10",        "YEAR": "201909",        "VERSION": 0,        "SUBJECTNAME": "富阳区第一人民医院",        "USERNAME": "盛丹虹",        "CREATEDATE": "20191123151844",        "ID": "402881916e971509016e9720917e001f",        "LASTMODIFIEDTIME": "20191123151844",        "SUBJECTCODE": "0",        "NAME": ""    }],"total": 22,"pageable": {    "pageNumber": 0,    "pageSize": 10,    "searchProperty": null,    "searchValue": null,    "orderProperty": null,    "orderDirection": null,    "orders": []},"orders": [],"pageNumber": 0,"searchValue": null,"pageSize": 10,"searchProperty": null,"orderProperty": null,"totalPages": 3,"orderDirection": null
     *     }
     * }
     */
    @PostMapping("/list")
    public JsonResult list(@RequestParam(defaultValue = "") String time,
                           @RequestParam(defaultValue = "") String code,
                           @RequestParam(defaultValue = "0") int num,
                           @RequestParam(defaultValue = "10") int size) {
        return drugsService.findList(time, code, num, size, jsonResult);
    }

}
