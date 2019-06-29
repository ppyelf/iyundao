package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.SpiritContent;
import com.ayundao.entity.SpiritFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: SpiritContentRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 5:43
 * @Description: 仓库 - 党内精神正文
 * @Version: V1.0
 */
@Repository
public interface SpiritContentRepository extends BaseRepository<SpiritContent, String> {

    //根据党内精神ID查询实体
    @Query("select sc from SpiritContent sc where sc.spirit.id = (?1)")
    SpiritContent findBySpiritId(String id);

    //获取正文的部分信息
    @Query("select substring(sc.content, 1, 10) from SpiritContent sc where sc.spirit.id = (?1)")
    String getContentBySpiritId(String id);
}
