package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: MessageRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 消息发布
 * @Version: V1.0
 */
@Repository
public interface MessageRepository extends BaseRepository<Message,String> {

    @Query("select ma from Message ma where ma.type = ?1")
    List<Message> findByType(Message.TYPE type);
}
