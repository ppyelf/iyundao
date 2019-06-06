package com.ayundao.service;

import com.ayundao.entity.Sign;

import java.util.List;

/**
 * @ClassName: SignService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/6 10:09
 * @Description: 服务 - 签到
 * @Version: V1.0
 */
public interface SignService {

    /**
     * 根据活动ID获取签到集合
     * @param id
     * @return
     */
    List<Sign> findByActivityId(String id);
}
