package com.ayundao.service;

import com.ayundao.entity.Consume;

import java.util.List;

/**
 * @ClassName: ConsumeService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 15:31
 * @Description: 服务 - 耗材预警
 * @Version: V1.0
 */
public interface ConsumeService {

    List<Consume> saveAllConsume(List<Consume> list);

    /**
     * 耗材预警排名
     * @param type
     * @return
     */
    List<Consume> findOrderByCreatedTime(int type);
}
