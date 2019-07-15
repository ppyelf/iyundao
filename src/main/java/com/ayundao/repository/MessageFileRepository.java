package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MessageFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: MessageFileRepository
 * @project: ayundao
 * @author: King
 * @Date: 2019/6/14 9:00
 * @Description: 仓库 - 消息发布 -文件附件表
 * @Version: V1.0
 */
@Repository
public interface MessageFileRepository extends BaseRepository<MessageFile,String> {


    /**
     * 根据消息发布详情查询附属文件
     * @param id
     * @return
     */
    @Query("select maf from MessageFile maf where maf.message.id = ?1")
    List<MessageFile> findByMessage(String id);
}
