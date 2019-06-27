package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.SpiritSubject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: SpiritSubjectRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 5:44
 * @Description: 仓库 - 党内精神机构
 * @Version: V1.0
 */
@Repository
public interface SpiritSubjectRepository extends BaseRepository<SpiritSubject, String> {

    //根据党内精神ID查询实体集合
    @Query("select ss from SpiritSubject ss where ss.spirit.id = (?1)")
    SpiritSubject findBySpiritId(String id);
}
