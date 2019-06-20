package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Indicator;
import com.ayundao.entity.WorkSubject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: IndicatorRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 19:06
 * @Description: 仓库 - 工作指标
 * @Version: V1.0
 */
@Repository
public interface IndicatorRepository extends BaseRepository<Indicator, String> {

    //根据工作ID查询实体集合信息
    @Query("select i from Indicator i where i.work.id = (?1)")
    List<Indicator> findByWorkId(String id);

    //根据父级ID获取获取父级实体
    @Query("select i from Indicator i where i.father.id = (?1)")
    List<Indicator> findByFatherId(String fatherId);

    //根据father为空获取所有实体集合
    @Query("select i from Indicator i where i.father is null")
    List<Indicator> findAllAndFatherIsNull();
}
