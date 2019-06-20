package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Indicator;
import com.ayundao.entity.IndicatorInfo;
import com.ayundao.entity.WorkSubject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: IndicatorInfoRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 19:10
 * @Description: 仓库 - 指标详情
 * @Version: V1.0
 */
@Repository
public interface IndicatorInfoRepository extends BaseRepository<IndicatorInfo, String> {

    //根据工作ID查询实体集合信息
    @Query("select ii from IndicatorInfo ii where ii.indicator in (?1)")
    List<IndicatorInfo> findByIndicator(List<Indicator> id);
}
