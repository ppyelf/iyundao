package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.IndicatorInfo;
import com.ayundao.entity.IndicatorInfoFile;
import com.ayundao.entity.IndicatorInfoImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: IndicatorInfoImageRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 19:12
 * @Description: 仓库 - 指标详情图片
 * @Version: V1.0
 */
@Repository
public interface IndicatorInfoImageRepository extends BaseRepository<IndicatorInfoImage, String> {

    //根据指标详情ID查询实体集合信息
    @Query("select iii from IndicatorInfoImage iii where iii.indicatorInfo in (?1)")
    List<IndicatorInfoImage> findByIndicatorInfo(List<IndicatorInfo> indicatorInfos);
}
