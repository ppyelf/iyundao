package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Message;
import com.ayundao.entity.MessageFile;
import com.ayundao.entity.MessageImage;

import java.util.List;

/**
 * @ClassName: MessageService
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/12 9:00
 * @Description: 服务层 -- 消息发布
 * @Version: V1.0
 */
public interface MessageService {

    /**
     * 新增消息发布
     * @param messageImages
     * @param messageFiles
     * @param message
     * @param jsonResult
     * @return
     */
    JsonResult saveAll(List<MessageImage> messageImages, List<MessageFile> messageFiles, Message message,JsonResult jsonResult);

    /**
     * 查询所有消息发布的附属文件
     * @param ids
     * @return
     */
    List<MessageFile> findFileByIds(String [] ids);

    /**
     * 查询所有消息发布的附属图片
     * @param ids
     * @return
     */
    List<MessageImage> findImageByIds(String [] ids);

    /**
     * 新增消息发布附件文件
     * @param messageFile
     * @return
     */
    MessageFile saveFile(MessageFile messageFile);

    /**
     * 新增消息发布附件图片
     * @param messageImage
     * @return
     */
    MessageImage saveImage(MessageImage messageImage);

    /**
     * 删除消息发布详情
     * @param id
     */
    void delete (String id);

    /**
     * 删除消息发布详情的附属图片
     * @param id
     */
    void deleteByImage(String id);

    /**
     * 删除消息发布详情的附属文件
     * @param id
     */
    void deleteByFile(String id);

    /**
     * 查询所有消息发布详情
     * @return
     */
    List<Message> findAll();

    /**
     * 根据状态查询消息发布详情
     */
    List<Message> findAllType(String type);

    /**
     * 查询单条消息发布详情
     * @param id
     * @return
     */
    Message findById(String id);

    /**
     * 查询单条消息发布详情的附属图片
     * @param messageId
     * @return
     */
    List<MessageImage> findByMessageIdI(String messageId);

    /**
     * 查询单条消息发布详情的附属文件
     * @param messageId
     * @return
     */
    List<MessageFile> findByMessageIdF(String messageId);

    /**
     * 根据ID查询附属图片
     * @param id
     * @return
     */
    MessageImage findByIdI(String id);

    /**
     * 根据ID查询附属文件
     * @param id
     * @return
     */
    MessageFile findByIdF(String id);

}
