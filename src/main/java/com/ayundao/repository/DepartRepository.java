package com.ayundao.repository;

import com.ayundao.entity.Depart;
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

    @Query("select d from Depart d where d.subject.id = ?1")
    List<Depart> findBySubjectId(String subjectId);


    @Query("select d from Depart d where d.id = ?1")
    Depart findByDepartId(String departId);
}
