package com.ayundao.service;

import com.ayundao.base.utils.JsonResult;
import com.ayundao.entity.StyleOfWork;
import com.ayundao.entity.StyleOfWorkRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: ink-feather
 * @Description: 行风效能
 * @Date: 2019/6/12 15:52
 */
public interface StyleOfWorkService {

    /**
     * 保存或修改实体
     * @param params
     * @return
     */
    StyleOfWork save(StyleOfWork params);

    JsonResult save(StyleOfWorkRecord params);

    /**
     * 查询所有
     * @return
     */
    Page<StyleOfWork> selectAll(Pageable pageable);

    /**
     * 根据行风效能id查询扣分明细
     * @param id
     * @return
     */
    JsonResult findByWorkId(String id);

    /**
     * 根据部门进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<StyleOfWork> selectByDepart(String params,Pageable pageable);

    /**
     * 根据组织进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<StyleOfWork> selectByGroup(String params,Pageable pageable);

    /**
     * 根据机构进行查询,分页
     * @param params
     * @param pageable
     * @return
     */
    Page<StyleOfWork> selectBySubject(String params,Pageable pageable);

    /**
     *分组织统计科室行风效能平均分 倒序
     * @return
     */
    JsonResult statistics();
}
