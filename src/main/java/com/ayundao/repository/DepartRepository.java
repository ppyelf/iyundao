package com.ayundao.repository;

import com.ayundao.entity.Depart;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.repository.Query;
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
public interface DepartRepository extends CrudRepository<Depart, String> {

    //获取机构的部门列表
    @Query("select d from Depart d where d.subject.id = ?1")
    List<Depart> findBySubjectId(String subjectId);

    //根据ID获取部门实体
    @Query("select d from Depart d where d.id = ?1")
    Depart findByDepartId(String departId);

    //获取所有部门列表
    @Query("select d from Depart d")
    List<Depart> getList();

}
