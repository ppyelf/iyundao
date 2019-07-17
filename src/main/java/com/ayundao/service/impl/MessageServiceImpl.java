package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Message;
import com.ayundao.entity.MessageFile;
import com.ayundao.entity.MessageImage;
import com.ayundao.repository.MessageFileRepository;
import com.ayundao.repository.MessageImageRepository;
import com.ayundao.repository.MessageRepository;
import com.ayundao.service.MessageService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.misc.ConstructorUtil;

import java.rmi.MarshalException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: MessageServiceImpl
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 服务实现 - 用户详情
 * @Version: V1.0
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageImageRepository messageImageRepository;

    @Autowired
    private MessageFileRepository messageFileRepository;

    @Override
    @Transactional
    public JsonResult saveAll(List<MessageImage> messageImages, List<MessageFile> messageFiles, Message message,JsonResult jsonResult) {

        if (CollectionUtils.isNotEmpty(messageImages)) {
            Set<MessageImage> messageImage = new HashSet<>();
            for (MessageImage image : messageImages) {
                image.setMessage(message);
                image = messageImageRepository.save(image);
            }
            message.setMessageImages(messageImage);
        }
        Set<MessageFile> messageFile = new HashSet<>();
        if(CollectionUtils.isNotEmpty(messageFiles)){
            for (MessageFile file : messageFiles) {
                file.setMessage(message);
                file = messageFileRepository.save(file);
            }
            message.setMessageFiles(messageFile);
        }
        message = messageRepository.save(message);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",message);
        jsonResult.setData(jsonObject);
        return jsonResult;
    }

    @Override
    public List<MessageFile> findFileByIds(String[] ids) {
        return messageFileRepository.findByIds(ids);
    }

    @Override
    public List<MessageImage> findImageByIds(String[] ids) {
        return messageImageRepository.findByIds(ids);
    }

    @Override
    public MessageFile saveFile(MessageFile messageFile) {
        return messageFileRepository.save(messageFile);
    }

    @Override
    public MessageImage saveImage(MessageImage messageImage) {
        return messageImageRepository.save(messageImage);
    }

    @Override
    public void delete(String id) {
        Message message = messageRepository.find(id);
//        List<MessageImage> messageImages = messageImageRepository.findByMessage(id);
//        if (messageImages != null){
//            for (MessageImage messageImage : messageImages) {
//                messageImageRepository.delete(messageImage);
//            }
//        }
//        List<MessageFile> messageFiles = messageFileRepository.findByMessage(id);
//        if (messageFiles != null){
//            for (MessageFile messageFile : messageFiles) {
//                messageFileRepository.delete(messageFile);
//            }
//        }
        messageRepository.delete(message);
    }

    @Override
    public void deleteByImage(String id) {
        MessageImage messageImage = messageImageRepository.find(id);
        if (messageImage != null){
            messageImageRepository.delete(messageImage);
        }
    }

    @Override
    public void deleteByFile(String id) {
        MessageFile messageFile = messageFileRepository.find(id);
        if(messageFile != null){
            messageFileRepository.delete(messageFile);
        }
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findAllType(int type){

        for (Message.TYPE value : Message.TYPE.values()) {
            if(value.ordinal()==(type)){
                return messageRepository.findByType(value);
            }
        }
        return null;
    }

    @Override
    public Message findById(String id) {
        return messageRepository.find(id);
    }

    @Override
    public List<MessageImage> findByMessageIdI(String messageId) {
        return messageImageRepository.findByMessage(messageId);
    }

    @Override
    public List<MessageFile> findByMessageIdF(String messageId) {
        return messageFileRepository.findByMessage(messageId);
    }

    @Override
    public MessageImage findByIdI(String id) {
        return messageImageRepository.find(id);
    }

    @Override
    public MessageFile findByIdF(String id) {
        return messageFileRepository.find(id);
    }

    @Override
    public Message saveMessage(Message message) {

        return messageRepository.save(message);
    }


}
