package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.IndicatorInfo;
import com.ayundao.entity.IndicatorInfoFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: IndicatorInfoFileRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 19:11
 * @Description: 仓库 - 指标详情文件
 * @Version: V1.0
 */
@Repository
public interface IndicatorInfoFileRepository extends BaseRepository<IndicatorInfoFile, String> {

    //根据指标详情查询集合信息
    @Query("select iif from IndicatorInfoFile iif where iif.indicatorInfo in (?1)")
    List<IndicatorInfoFile> findByIndicatorInfo(List<IndicatorInfo> indicatorInfos);
}
