package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Depart;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: DepartRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 14:26
 * @Description: 仓库 - 部门
 * @Version: V1.0
 */
@Repository
public interface DepartRepository extends BaseRepository<Depart, String> {

    //获取机构的部门列表
    @Query(value = "select d from Depart d  where d.subject.id = ?1")
    List<Depart> findBySubjectId(String subjectId);

    //根据ID获取部门实体
    @Query("select d from Depart d where d.id = ?1")
    Depart findByDepartId(String departId);

    //获取所有部门列表
    @Query("select d from Depart d")
    List<Depart> getList();

    //获取机构为空的部门列表
    @Query("select d from Depart d where d.subject is null")
    List<Depart> findSubjectIsNull();

    //根据父级ID获取子集
    @Query("select d from Depart d where d.father.id = (?1)")
    List<Depart> findByFatherId(String id);

    //根据父级ID为空获取实体集合
    @Query("select d from Depart d where d.father is null")
    List<Depart> getListByFatherIdIsNull();

    //获取机构的部门列表
    @Query(value = "select d from Depart d  where d.subject.id = ?1 and d.father is null")
    List<Depart> findBySubjectIdAndFatherIsNull(String subjectId);
}
