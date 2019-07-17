package com.ayundao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseController;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.FileUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Assessment;
import com.ayundao.entity.Message;
import com.ayundao.entity.MessageFile;
import com.ayundao.entity.MessageImage;
import com.ayundao.service.MessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MessageController
 * @project: ayundao
 * @author: King
 * @Date: 2019/5/13 16:24
 * @Description: 控制层 - 消息发布
 * @Version: V1.0
 */
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    /**
     * @api {post} /message/add 新增消息发布详情
     * @apiGroup Message
     * @apiVersion 1.0.0
     * @apiDescription 新增消息发布详情
     * @apiParam {int} type 审核类型
     * @apiParam {String} title 标题
     * @apiParam {String} branch 发布支部
     * @apiParam {String} publisher 发布人员
     * @apiParam {String} author 要闻作者
     * @apiParam {String} articleTime 发布时间
     * @apiParam {String} articleIntroduce 要闻简介
     * @apiParam {String} article 要闻详情
     * @apiParam {String} userId 审核人Id
     * @apiParam {String[]} messageImageIds 图片ID
     * @apiParam {String[]} messageFileIds 文件ID
     * @apiParamExample {json} 请求样例：
     *                /message/add?
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 404:已存在该机构</br>
     *                                 600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "message": {
     *             "id": "2c95d6816be92e5c016be93e7605000c",
     *             "messageFiles": [],
     *             "messageImages": null,
     *             "branch": "测试",
     *             "publisher": "测试",
     *             "author": "测试",
     *             "articleTime": "测试",
     *             "articleIntroduce": "测试",
     *             "type": "NOT",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         }
     *     }
     * }
     */
    @PostMapping(value = "/add")
    public JsonResult add(@RequestParam(defaultValue = "0") int type,
                          String title,
                          String branch,
                          String publisher,
                          String author,
                          String articleTime,
                          String articleIntroduce,
                          String article,
                          String userId,
                          String [] messageImageIds,
                          String [] messageFileIds){
        Message message = new Message();
        message.setCreatedDate(new Date());
        message.setLastModifiedDate(new Date());
        message.setBranch(branch);
        message.setPublisher(publisher);
        message.setAuthor(author);
        message.setArticleTime(articleTime);
        message.setArticleIntroduce(articleIntroduce);
        message.setArticle(article);
        message.setUserId(userId);
        message.setTitle(title);
        for (Message.TYPE type1 : Message.TYPE.values()) {
            if (type1.ordinal() == type){
                message.setType(type1);
                break;
            }
        }
        List<MessageImage> messageImages = messageService.findImageByIds(messageImageIds);
        List<MessageFile> messageFiles = messageService.findFileByIds(messageFileIds);

        return messageService.saveAll(messageImages,messageFiles,message,jsonResult);
    }

    /**
      * @api {POST} /message/upload_file 上传文件
      * @apiGroup MessageFile
      * @apiVersion 1.0.0
      * @apiDescription 上传文件
      * @apiParam {MultipartFile} file
      * @apiParamExample {json} 请求样例:
      *                message/upload_file
      * @apiSuccess (200) {String} code 200:成功</br>
      *                                 601:名称,路径或后缀名不能为空</br>
      *                                 602:文件类型异常</br>
      * @apiSuccess (200) {String} message 信息
      * @apiSuccess (200) {String} data 返回用户信息
      * @apiSuccessExample {json} 返回样例:
      * {
      *     "code": 200,
      *     "message": "成功",
      *     "data": {
      *         "name": "b7793c5c503443089b6facdf64129c1d",
      *         "id": "2c95d6816be8e55e016be8f84aea0000",
      *         "suffix": "jpg",
      *         "url": "d:\\upload\\messagefile\\b7793c5c503443089b6facdf64129c1d.jpg"
      *     }
      * }
      */
    @PostMapping("/upload_file")
    public JsonResult uploadFile(MultipartFile file){
        MessageFile files = new MessageFile();
        files.setCreatedDate(new Date());
        files.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, files, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        files.setName(map.get("name"));
        files.setUrl(map.get("url"));
        files.setSuffix(map.get("suffix"));
        files = messageService.saveFile(files);
        jsonResult.setData(getUploadJson(files));
        return jsonResult;
    }

    /**
     * @api {POST} /message/upload_image 上传图片
     * @apiGroup MessageImage
     * @apiVersion 1.0.0
     * @apiDescription 上传图片
     * @apiParam {MultipartFile} file
     * @apiParamExample {json} 请求样例:
     *                /message/upload_image
     * @apiSuccess (200) {String} code 200:成功</br>
     *                                 601:名称,路径或后缀名不能为空</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     *{
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "name": "d4027cfbc22148c9a1041a4596596ef6",
     *         "id": "2c95d6816be8e55e016be8f942620001",
     *         "suffix": "jpg",
     *         "url": "d:\\upload\\messageimage\\d4027cfbc22148c9a1041a4596596ef6.jpg"
     *     }
     * }
     */
    @PostMapping("/upload_image")
    public JsonResult uploadImage(MultipartFile file) {
        MessageImage image = new MessageImage();
        image.setCreatedDate(new Date());
        image.setLastModifiedDate(new Date());
        Map<String, String> map = FileUtils.uploadFile(file, image, uploadPath);
        if (map == null) {
            return JsonResult.failure(601, "上传失败");
        }
        image.setName(map.get("name"));
        image.setUrl(map.get("url"));
        image.setSuffix(map.get("suffix"));
        image = messageService.saveImage(image);
        jsonResult.setData(getUploadJson(image));
        return jsonResult;
    }

    /**
     * @api {Post} /message/del 删除用户详情
     * @apiGroup Message
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 用户ID
     * @apiParamExample {json} 请求样例
     *                ?id=xxx
     * @apiSuccess (200) {int} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "message": {
     *             "id": "2c95d6816be92e5c016be93e7605000c",
     *             "messageFiles": [],
     *             "messageImages": null,
     *             "branch": "测试",
     *             "publisher": "测试",
     *             "author": "测试",
     *             "articleTime": "测试",
     *             "articleIntroduce": "测试",
     *             "type": "NOT",
     *             "info1": null,
     *             "info2": null,
     *             "info3": null,
     *             "info4": null,
     *             "info5": null,
     *             "new": false
     *         }
     *     }
     * }
     */
    @PostMapping("/del")
    public JsonResult del(String id){
        if (StringUtils.isBlank(id)){
            return JsonResult.paramError();
        }
        messageService.delete(id);
        return jsonResult;
    }

    /**
     * @api {Post} /message/del_file 删除消息发布 -附件文件
     * @apiGroup MessageFile
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 用户ID
     * @apiParamExample {json} 请求样例
     *                ?id=xxx
     * @apiSuccess (200) {int} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": ""
     * }
     */
    @PostMapping("/del_file")
    public JsonResult del_file(String id){
        if(StringUtils.isBlank(id)){
            return JsonResult.paramError();
        }
        messageService.deleteByFile(id);
        return jsonResult;
    }

    /**
     * @api {Post} /message/del_image 删除消息发布 -附件图片
     * @apiGroup MessageImage
     * @apiVersion 1.0.0
     * @apiDescription 删除
     * @apiParam {String} id 用户ID
     * @apiParamExample {json} 请求样例
     *                ?id=xxx
     * @apiSuccess (200) {int} code 200:成功</br>
     *                                 201:用户名密码错误</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     * 	"code": 200,
     * 	"message": "成功",
     * 	"data": ""
     * }
     */
    @PostMapping("/del_image")
    public JsonResult del_image(String id){
        if(StringUtils.isBlank(id)){
            return JsonResult.paramError();
        }
        messageService.deleteByImage(id);
        return jsonResult;
    }


    /**
     * @api {post} /message/list 查询消息发布详情
     * @apiGroup Message
     * @apiVersion 1.0.0
     * @apiDescription 查询消息发布详情
     * @apiParamExample {json} 请求样例
     *                /message/list
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *     ]
     * }
     */
    @GetMapping("/list")
    public JsonResult list(){
        List<Message> pages = messageService.findAll();
        JSONArray pageArray = new JSONArray();
        for (Message message : pages) {
            JSONObject json = new JSONObject(JsonUtils.getJson(message));
            pageArray.add(json);
        }
        jsonResult.setData(pageArray);
        return jsonResult;
    }

    /**
     * @api {post} /message/list_type 根据状态查询消息发布详情
     * @apiGroup Message
     * @apiVersion 1.0.0
     * @apiDescription 根据状态查询消息发布详情
     * @apiParam {int} type 类型id 0 -未审核 1 -审核同意 2 -审核未通过
     * @apiParam {int} page 当前的页面
     * @apiParam {int} size 每页数量
     * @apiParamExample {json} 请求样例
     *                /message/list_type?type=审核通过
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *     ]
     * }
     */
    @PostMapping("/list_type")
    public JsonResult list_type(int type,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size ){
        List<Message> messages = messageService.findAllType(type);
        List<Message> currentPageList = new ArrayList<>();
        if (messages != null && messages.size()>0){
            int currIdx = (page > 1 ? (page - 1) * size : 0);
            for (int i = 0; i < size && i < messages.size() - currIdx; i++) {
                Message data = messages.get(currIdx + i);
                currentPageList.add(data);
            }
        }

//        Page<Message> messagePage = new Page<>();
//        JSONObject jsonObject = JsonUtils.getPage(messagePage);
//        System.out.println("aaa:"+jsonObject);
//        jsonObject.put("total",messages.size());
//        String aaa =messages.size()==0?"0":"1";
//        jsonObject.put("totalPage", aaa);
//        jsonObject.put("page",0);
//        jsonObject.put("content",converMessage(messages));
//        jsonResult.setData(jsonObject);
        jsonResult.setData(converMessage(currentPageList));
        return jsonResult;
    }

    /**
     * @api {post} /message/findId 单条消息发布详情
     * @apiGroup Message
     * @apiVersion 1.0.0
     * @apiDescription 单条消息发布详情
     * @apiParamExample {json} 请求样例
     *                /message/findId?id=
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *     ]
     * }
     */
    @PostMapping("/findId")
    public JsonResult findId(String id){
        if (StringUtils.isBlank(id)){
            return JsonResult.paramError();
        }
        JSONObject jsonObject = new JSONObject();
        Message message = messageService.findById(id);
        List<MessageImage> messageImages = messageService.findByMessageIdI(id);
        List<MessageFile> messageFiles = messageService.findByMessageIdF(id);
        if(messageImages != null){
            for (MessageImage messageImage : messageImages) {
                jsonObject.put("messageImage",messageImage);
            }
        }
        if(messageFiles != null){
            for (MessageFile messageFile : messageFiles) {
                jsonObject.put("messageFile",messageFile);
            }
        }
        jsonObject.put("message",message);
        JSONArray array = new JSONArray();
        array.add(jsonObject);
        jsonResult.setData(array);
        return jsonResult;
    }

    /**
     * @api {post} /message/findIdf 消息发布详情 -单条文件信息
     * @apiGroup MessageFile
     * @apiVersion 1.0.0
     * @apiDescription 消息发布详情 -单条文件信息
     * @apiParamExample {json} 请求样例
     *                /message/findIdf?id=
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *     ]
     * }
     */
    @PostMapping("/findIdf")
    public JsonResult findIdf(String id){
        if(StringUtils.isBlank(id)){
            return JsonResult.paramError();
        }
        MessageFile messageFile = messageService.findByIdF(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageFile",messageFile);
        jsonResult.setData(jsonObject);
        return jsonResult;
    }

    /**
     * @api {post} /message/findIdi 消息发布详情 -单条图片信息
     * @apiGroup MessageImage
     * @apiVersion 1.0.0
     * @apiDescription 消息发布详情 -单条图片信息
     * @apiParamExample {json} 请求样例
     *                /message/findIdi?id=
     * @apiSuccess (200) {int} code 200:成功</br>
     *                              600:参数异常</br>
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} data 返回用户信息
     * @apiSuccessExample {json} 返回样例:
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": [
     *     ]
     * }
     */
    @PostMapping("/findIdi")
    public JsonResult findIdi(String id){
        if(StringUtils.isBlank(id)){
            return JsonResult.paramError();
        }
        MessageImage messageImage = messageService.findByIdI(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageImage",messageImage);
        jsonResult.setData(jsonObject);
        return jsonResult;
    }

   /**
   * @api {POST} /message/audit 审核
   * @apiGroup Message
   * @apiVersion 1.0.0
   * @apiDescription 查看
   * @apiParam {String} id 必填
   * @apiParam {int} type 要改变的类型
    * * 0 -未审核
    * 1 -审核通过
    * 2 -审核拒绝
   * @apiParam {String} userAssess 审核人的评价
   * @apiParamExample {json} 请求样例:
   *                /message/audit?id=1122&type=2&userAssess=这是一堆数据
   * @apiSuccess (200) {String} code 200:成功</br>
   * @apiSuccess (200) {String} message 信息
   * @apiSuccess (200) {String} data 返回用户信息
   * @apiSuccessExample {json} 返回样例:
   * {
   *     "code": 200,
   *     "message": "成功",
   *       "data": {"articleIntroduce": "33","articleTime": "33","author": "33","publisher": "33","id": "1122","type": "NO","title": "33","branch": "33","userId": "33","article": "33","userAssess": "这是一堆数据"}
   * }
   */
    @PostMapping("/audit")
    public JsonResult audit(String id,
                            int type,
                            String userAssess){
            Message message = messageService.findById(id);
            if (message==null){
                return JsonResult.notFound("找不到该信息");
            }
            if (type != 0 && type != 1 && type !=2){
                return JsonResult.notFound("数据类型异常");
            }
            message.setUserAssess(userAssess);
        for (Message.TYPE type1 : Message.TYPE.values()) {
            if (type1.ordinal() == type) {
                message.setType(type1);
                break;
            }
        }

        message = messageService.saveMessage(message);
            jsonResult.setData(JsonUtils.getJson(message));
        return  jsonResult;
    }

    /**
     * List<Message>转JSONARRAY
     * @param messages
     * @return
     */
    private JSONArray converMessage(List<Message> messages){
        JSONArray arr = new JSONArray();
        for (Message message : messages){
            JSONObject object = new JSONObject();
            object.put("id",message.getId());
            object.put("type",message.getType());
            object.put("userId",message.getUserId());
            object.put("title",message.getTitle());
            object.put("branch",message.getBranch());
            object.put("publisher",message.getPublisher());
            object.put("author",message.getAuthor());
            object.put("article",message.getArticle());
            object.put("articleTime",message.getArticleTime());
            object.put("articleIntroduce",message.getArticleIntroduce());
            object.put("userAssess",message.getUserAssess());
            arr.add(object);
        }
        return arr;
    }
}
