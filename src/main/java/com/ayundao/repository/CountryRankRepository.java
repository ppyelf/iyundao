package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.CountryRank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: CountryRankRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 11:28
 * @Description: 仓库 - 学习强国
 * @Version: V1.0
 */
@Repository
public interface CountryRankRepository extends BaseRepository<CountryRank, String> {

    //根据起止时间查询并按照分数排序
    @Query("select cr from CountryRank cr where cr.createdDate between (?1) and (?2) order by cr.score desc")
    List<CountryRank> findListByCreatedTimeOrderByScore(String startTime, String endTime);
}
