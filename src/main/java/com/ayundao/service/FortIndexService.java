package com.ayundao.service;

import com.ayundao.entity.FortIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: ink-feather
 * @Description: 堡垒指数
 * @Date: 2019/6/12 16:29
 */
public interface FortIndexService {

    /**
     * 保存或修改实体
     * @param params
     * @return
     */
    FortIndex save(FortIndex params);

    /**
     * 查询所有
     * @return
     */
    Page<FortIndex> selectAll(Pageable pageable);

    /**
     * 根据部门进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<FortIndex> selectByDepart(String params,Pageable pageable);

    /**
     * 根据组织进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<FortIndex> selectByGroup(String params,Pageable pageable);

    /**
     * 根据机构进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<FortIndex> selectBySubject(String params,Pageable pageable);

}
