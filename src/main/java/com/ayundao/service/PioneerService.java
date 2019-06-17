package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.Pioneer;
import com.ayundao.entity.PioneerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: ink-feather
 * @Description: 先锋人物
 * @Date: 2019/6/12 16:29
 */
public interface PioneerService {

    /**
     * 保存或修改实体
     * @param params
     * @return
     */
    Pioneer save(Pioneer params);

    JsonResult save(PioneerType params);

    /**
     * 查询所有
     * @return
     */
    Page<Pioneer> selectAll(Pageable pageable);

    /**
     * 根据部门进行查询,分页
     * @param departId
     * @return
     */
    Page<Pioneer> selectByDepart(String departId,Pageable pageable);

    /**
     * 根据组织进行查询,分页
     * @param departId
     * @return
     */
    Page<Pioneer> selectByGroup(String departId,Pageable pageable);

    /**
     * 根据机构进行查询,分页
     * @param departId
     * @return
     */
    Page<Pioneer> selectBySubject(String departId,Pageable pageable);

    /**
     * 根据部门id查询类别
     * @param departId
     * @return
     */
    JsonResult selectTypeByDepart(String departId);

    /**
     * 根据组织id查询类别
     * @param departId
     * @return
     */
    JsonResult selectTypeByGroup(String departId);

    /**
     * 根据机构id查询类别
     * @param departId
     * @return
     */
    JsonResult selectTypeBySubject(String departId);

    /**
     * 根据类别查询数据
     * @param typeId
     * @return
     */
    JsonResult findByType(String typeId,Pageable pageable);

}
