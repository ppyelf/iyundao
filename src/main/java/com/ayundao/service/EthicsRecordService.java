package com.ayundao.service;

import com.ayundao.entity.EthicsRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: ink-feather
 * @Description:
 * @Date: 2019/6/12 15:33
 */
public interface EthicsRecordService {


    /**
     * 保存或修改实体
     * @param params
     * @return
     */
    EthicsRecord save(EthicsRecord params);

    /**
     * 查询所有
     * @return
     */
    Page<EthicsRecord> selectAll(Pageable pageable);

    /**
     * 根据部门进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<EthicsRecord> selectByDepart(String params,Pageable pageable);

    /**
     * 根据组织进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<EthicsRecord> selectByGroup(String params,Pageable pageable);

    /**
     * 根据机构进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<EthicsRecord> selectBySubject(String params,Pageable pageable);
}
