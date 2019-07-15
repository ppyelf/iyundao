package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MessageImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: MessageImageRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 消息发布 -图片附件表
 * @Version: V1.0
 */
@Repository
public interface MessageImageRepository extends BaseRepository<MessageImage,String> {

    @Query("select mai from MessageImage mai where mai.message.id = ?1")
    List<MessageImage> findByMessage(String id);
}
