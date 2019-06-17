package com.ayundao.controller;

import com.ayundao.base.BaseController;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Field;
import com.ayundao.entity.Menu;
import com.ayundao.entity.Page;
import com.ayundao.service.FieldService;
import com.ayundao.service.MenuService;
import com.ayundao.service.PageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import sun.security.util.Length;

import javax.persistence.Id;
import javax.rmi.CORBA.Tie;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: PageController
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/28 23:22
 * @Description: 控制层 -- 页面
 * @Version: V1.0
 */
@RestController
@RequestMapping("/page")
public class PageController extends BaseController {

    @Autowired
    private PageService pageService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private MenuService menuService;

    /**
     * @api {get} /page/list 列表
     * @apiGroup Page
     * @apiVersion 1.0.0
     * @apiDescription 列表
     * @apiParamExample {json} 请求样例：
     *                /page/list
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     */
    @GetMapping("/list")
    public JsonResult list() {
        List<Page> pages = pageService.getAllForList();
        JSONArray arr = new JSONArray();
        for (Page page : pages) {
            arr.put(JsonUtils.getJson(page));
        }
        jsonResult.setData(JsonUtils.delString(arr.toString()));
        return jsonResult;
    }

    /**
     * @api {post} /page/view 查看
     * @apiGroup Page
     * @apiVersion 1.0.0
     * @apiDescription 查看
     * @apiParamExample {json} 请求样例：
     *                /page/view
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此用户组</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "操作成功",
     *     "data": "{"version":"1","id":"2c4824f71b6f4de389e0b8b375636d94","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"测试分院页面","level":"0","sort":"0","title":"管理员页面","uri":"","fields":[{"version":"1","id":"39b22ac71cb9490584f3fa4c5cf5b940","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"分组2字段","sort":"0"},{"version":"1","id":"bd5b0b97ba00400488c5a8aee29f9777","createdDate":"20190517111111","lastModifiedDate":"20190517111111","name":"分组1字段","sort":"0"}]}"
     * }
     */
    @PostMapping("/view")
    public JsonResult view(String id) {
        if (StringUtils.isBlank(id)) return JsonResult.paramError();
        return converJson(pageService.find(id), jsonResult);
    }

    /**
     * @api {post} /page/add 新增
     * @apiGroup Page
     * @apiVersion 1.0.0
     * @apiDescription 新增
     * @apiParam {String} name
     * @apiParamExample {json} 请求样例：
     *                /page/add
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:未查询到此菜单</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "操作成功",
     *     "data": "{"version":"0","id":"402881916b1ae347016b1c8d344a000f","createdDate":"20190603165544","lastModifiedDate":"20190603165544","name":"添加子页面","level":"1","sort":"0","title":"菜单标题","uri":""}"
     * }
     */
    @PostMapping("/add")
    public JsonResult add(String name,
                          String title,
                          String uri,
                          @RequestParam(defaultValue = "0") int level,
                          String menuId,
                          String fatherId,
                          @RequestParam(defaultValue = "0") int sort
                          ) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(menuId)) {
            return JsonResult.paramError();
        } 
        Page page = new Page();
        page.setCreatedDate(new Date(System.currentTimeMillis()));
        page.setLastModifiedDate(new Date(System.currentTimeMillis()));
        page.setName(name);
        page.setTitle(title);
        page.setUri(uri);
        page.setLevel(level);
        page.setSort(sort);
        Menu menu = menuService.findById(menuId);
        if (menu == null) {
            return JsonResult.notFound("未查询到此菜单");
        }
        Page father = pageService.find(fatherId);
        page = pageService.save(page, menu, father);
        jsonResult.setData(JsonUtils.getJson(page));
        return jsonResult;
    }

    /**
     * @api {post} /page/modify 修改
     * @apiGroup Page
     * @apiVersion 1.0.0
     * @apiDescription 修改
     * @apiParamExample {json} 请求样例：
     *                /page/modify
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 600:参数异常</br>
     *                                 601:页面不存在</br>
     *                                 602:菜单不存在</br>
     *                                 603:父级页面不存在</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "操作成功",
     *     "data": "{"version":"2","id":"402881916b1ae347016b1c8d344a000f","createdDate":"20190603165544","lastModifiedDate":"20190603165544","name":"修改子11页面","level":"0","sort":"0","title":"","uri":""}"
     * }
     */
    @PostMapping("/modify")
    public JsonResult modify(String id,
                             String name,
                             String title,
                             String uri,
                             @RequestParam(defaultValue = "0") int level,
                             String menuId,
                             String fatherId,
                             @RequestParam(defaultValue = "0") int sort) {
        Page page = pageService.find(id);
        if (page == null) {
            return JsonResult.failure(601, "页面不存在");
        }
        Menu menu = StringUtils.isBlank(menuId) ? page.getMenu() : menuService.findById(menuId);
        if (menu == null) {
            return JsonResult.failure(602, "菜单不存在");
        }
        Page father = StringUtils.isBlank(fatherId) ? page.getFather() : pageService.find(fatherId);
        if (StringUtils.isBlank(fatherId) && father == null) {
            return JsonResult.failure(603, "父级页面不存在");
        } 
        page.setName(name);
        page.setTitle(title);
        page.setUri(uri);
        page.setLevel(level);
        page.setSort(sort);
        page = pageService.save(page, menu, father);
        jsonResult.setData(JsonUtils.getJson(page));
        return jsonResult;
    }

    /**
     * @api {post} /page/del 删除
     * @apiGroup Page
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParamExample {json} 请求样例：
     *                /page/del
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 600:参数异常</br>
     *                                 601:页面不存在</br>
     *                                 602:请先删除子页面</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "操作成功",
     *     "data": "{}"
     * }
     */
    @PostMapping("/del")
    public JsonResult del(String id) {
        Page page = pageService.find(id);
        if (page == null) {
            return JsonResult.failure(601, "页面不存在");
        }
        List<Page> sons = pageService.findSonsByFatherId(page.getId());
        if (CollectionUtils.isNotEmpty(sons)) {
            return JsonResult.failure(602, "请先删除子页面");
        } 
        pageService.delete(id);
        return JsonResult.success();
    }

    /**
     * 实体的集合字段信息转换
     * @param page
     * @param jsonResult
     * @return
     */
    private JsonResult converJson(Page page, JsonResult jsonResult) {
        if (page == null) {
            return JsonResult.paramError();
        } 
        try {
            JSONObject json = new JSONObject(JsonUtils.getJson(page));
            if (page.getFather() != null) {
                json.put("father", JsonUtils.getJson(page.getFather()));
            }
            List<Field> fields = fieldService.findByPageId(page.getId());
            JSONArray arr = new JSONArray();
            if (CollectionUtils.isNotEmpty(fields)) {
                for (Field f : fields) {
                    arr.put(new JSONObject(JsonUtils.getJson(f)));
                }
            }
            json.put("fields", arr);
            jsonResult.setCode(200);
            jsonResult.setMessage("操作成功");
            jsonResult.setData(JsonUtils.delString(json.toString()));
            return jsonResult;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonResult.paramError();
    }

}
