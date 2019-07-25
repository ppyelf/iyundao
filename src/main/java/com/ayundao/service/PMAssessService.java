package com.ayundao.service;

import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.Pageable;
import com.ayundao.base.Page;
import com.ayundao.entity.DemocraticAppraisal;
import com.ayundao.entity.PioneerIndex;
import com.ayundao.entity.User;

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
     * 查询先锋指数L列表
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

    /**
     * 批量保存民主评议实体
     * @param list
     * @return
     */
    List<DemocraticAppraisal> saveAllDemocraticAppraisal(List<DemocraticAppraisal> list);

    /**
     * 查询民主评议列表
     * @param pageable
     * @return
     */
    Page<DemocraticAppraisal> findDemoForPage(Pageable pageable);

    /**
     * 民主评议搜索
     * @param pageable
     * @return
     */
    Page<DemocraticAppraisal> findDemoByProperty(Pageable pageable);

    /**
     * 查询某一年的所有先锋指数
     * @return
     */
    List<JSONObject> findPioForYear(String year);

    /**
     * 获得民主评议列表
     * @return
     */
    List<DemocraticAppraisal> findAllDemo();

    /**
     * 查询某一年的所有民主评议
     * @param year
     * @return
     */
    List<JSONObject> findDemForYear(String year);

    /**
     * 根据编号找到用户实体
     * @param usercode
     * @return
     */
    User findUserByusercode(String usercode);

    /**
     * 根据编号查找所有数据
     * @param user
     * @param usercode
     * @return
     */
    JSONObject findAllForPeople(User user, String usercode,String year);
}
