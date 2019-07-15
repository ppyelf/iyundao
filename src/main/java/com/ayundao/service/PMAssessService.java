package com.ayundao.service;

import com.ayundao.base.Pageable;
import com.ayundao.base.Page;
import com.ayundao.entity.PioneerIndex;

import java.util.List;

/**
 * @ClassName: PMAssessService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/13
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
public interface PMAssessService {

    /**
     * 批量保存先锋指数实体
     * @param list
     * @return
     */
    List<PioneerIndex> saveAllPioneerIndex(List<PioneerIndex> list);

    /**
     * 查询先锋指数排名
     * @param pageable
     * @return
     */
    Page<PioneerIndex> findAllForPage(Pageable pageable);


    /**
     * 先锋指数搜索
     * @param pageable
     * @return
     */
    Page<PioneerIndex> findByProperty(Pageable pageable);


    /**
     * 根据年月日搜索先锋指数详情
     * @param type
     * @return
     */
    List<PioneerIndex> findOrderByCreatedTime(int type);
}
