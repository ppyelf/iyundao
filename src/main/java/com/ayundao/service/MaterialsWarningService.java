package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.MaterialsRational;
import com.ayundao.entity.MaterialsWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: ink-feather
 * @Description: 耗材预警
 * @Date: 2019/6/12 15:43
 */
public interface MaterialsWarningService {

    /**
     * 保存或修改实体
     * @param params
     * @return
     */
    MaterialsWarning save(MaterialsWarning params);

    JsonResult save(MaterialsRational params);

    /**
     * 查询所有
     * @return
     */
    Page<MaterialsWarning> selectAll(Pageable pageable);

    /**
     * 根据耗材id查询点评
     * @param materialsId
     * @return
     */
    JsonResult findByMaterialsId(String materialsId);

    /**
     * 根据部门进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<MaterialsWarning> selectByDepart(String params,Pageable pageable);

    /**
     * 根据组织进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<MaterialsWarning> selectByGroup(String params,Pageable pageable);

    /**
     * 根据机构进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<MaterialsWarning> selectBySubject(String params,Pageable pageable);
}
