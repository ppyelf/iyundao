package com.ayundao.service;

import com.ayundao.entity.Depart;

import java.util.List;

/**
 * @ClassName: DepartService
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 14:24
 * @Description: 服务 -- 部门
 * @Version: V1.0
 */
public interface DepartService {

    /**
     * 根据机构ID获取部门集合
     * @param subjectId
     * @return
     */
    List<Depart> findBySubjectId(String subjectId);

    /**
     * 获取部门列表
     * @return
     */
    List<Depart> getList();

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    Depart findById(String id);

    /**
     * 保存实体
     * @param depart
     * @return
     */
    Depart save(Depart depart);

    /**
     * 根据ID集合获取集合信息
     * @param departIds
     * @return
     */
    List<Depart> findByIds(String[] departIds);

    /**
     * 保存所有实体
     * @param departs
     */
    List<Depart> saveAll(List<Depart> departs);

    /**
     * 获取机构为空的部门列表
     * @return
     */
    List<Depart> findSubjectIsNull();

    /**
     * 根据父级ID获取子集
     * @param id
     * @return
     */
    List<Depart> findByFatherId(String id);

    /**
     * 根据父级ID为空获取实体集合
     * @return
     */
    List<Depart> getListByFatherIdIsNull();

    /**
     * 根据subjectId获取所有父级实体集合
     * @param subjectId
     * @return
     */
    List<Depart> findBySubjectIdAndFatherIsNull(String subjectId);

    /**
     * 检测编号是否存在
     * @param code
     * @return
     */
    boolean existsCode(String code);

    /**
     * 根据编号查询实体
     */
    Depart findByCode(String code);

    /**
     * 查找部门集合
     * @param departIds
     * @return
     */
    List<Depart> findbyIds(String[] departIds);
}
