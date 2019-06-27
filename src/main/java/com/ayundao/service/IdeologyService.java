package com.ayundao.service;

import com.ayundao.entity.CountryRank;
import com.ayundao.entity.Pioneer;

import java.util.List;

/**
 * @ClassName: IdeologyService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 11:30
 * @Description: 服务 - 意识形态
 * @Version: V1.0
 */
public interface IdeologyService {

    /**
     * 批量保存学习强国实体
     * @param list
     * @return
     */
    List<CountryRank> saveAllCountry(List<CountryRank> list);

    /**
     * 批量保存先锋人物实体
     * @param list
     * @return
     */
    List<Pioneer> saveAllPioneer(List<Pioneer> list);

    /**
     * 获取学习强国排行
     * @param type
     * @return
     */
    List<CountryRank> findCountryOrderByCreatedTime(int type);

    /**
     * 获取先锋人物排名
     * @param type
     * @return
     */
    List<Pioneer> findPioneerOrderByCreatedTime(int type);
}
