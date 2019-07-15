package com.ayundao.service;

import com.ayundao.entity.AdvicesInfoDepar;

import java.util.List;

/**
 * @ClassName: AdvicesInfoDeparService
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/5
 * @Description: 实现 - 消息与部门组织结构表
 * @Version: V1.0
 */
public interface AdvicesInfoDeparService {


    /**
     * 根据消息id寻找关系
     * @param id
     * @return
     */
    List<AdvicesInfoDepar> findByAdvicesId(String id);
}
