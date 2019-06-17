package com.ayundao.service;

import com.ayundao.entity.PioneerIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: ink-feather
 * @Description: 先锋指数
 * @Date: 2019/6/12 16:29
 */
public interface PioneerIndexService {

    /**
     * 保存或修改实体
     * @param params
     * @return
     */
    PioneerIndex save(PioneerIndex params);

    /**
     * 查询所有
     * @return
     */
    Page<PioneerIndex> selectAll(Pageable pageable);

    /**
     * 根据部门进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<PioneerIndex> selectByDepart(String params,Pageable pageable);

    /**
     * 根据组织进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<PioneerIndex> selectByGroup(String params,Pageable pageable);

    /**
     * 根据机构进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<PioneerIndex> selectBySubject(String params,Pageable pageable);

}
