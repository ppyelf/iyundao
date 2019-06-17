package com.ayundao.service;

import com.ayundao.entity.PartySpirit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: ink-feather
 * @Description: 党内精神
 * @Date: 2019/6/12 16:28
 */
public interface PartySpiritService {

    /**
     * 保存或修改实体
     * @param params
     * @return
     */
    PartySpirit save(PartySpirit params);

    /**
     * 查询所有
     * @return
     */
    Page<PartySpirit> selectAll(Pageable pageable);

    /**
     * 根据部门进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<PartySpirit> selectByDepart(String params,Pageable pageable);

    /**
     * 根据组织进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<PartySpirit> selectByGroup(String params,Pageable pageable);

    /**
     * 根据机构进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<PartySpirit> selectBySubject(String params,Pageable pageable);

}
