package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.SpiritFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: SpiritFileRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 5:42
 * @Description: 仓库 - 党内精神附件
 * @Version: V1.0
 */
@Repository
public interface SpiritFileRepository extends BaseRepository<SpiritFile, String> {

    //根据党内精神ID查询实体集合
    @Query("select sf from SpiritFile sf where sf.spirit.id = (?1)")
    List<SpiritFile> findBySpiritId(String id);
}
