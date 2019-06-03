package com.ayundao.repository;

import com.ayundao.entity.Groups;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: GroupsRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/27 15:14
 * @Description: 仓库 - 小组
 * @Version: V1.0
 */
@Repository
public interface GroupsRepository extends CrudRepository<Groups, String> {

    //获取机构的所有小组集合
    @Query("select g from Groups g where g.subject.id = ?1")
    List<Groups> findBySubjectId(String subjectId);

    /**
     * 根据ID获取实体
     * @param groupsId
     * @return
     */
    @Query("select g from Groups g where g.id = ?1")
    Groups findByGroupsId(String groupsId);

    /**
     * 获取所有小组集合
     * @return
     */
    @Query("select g from Groups g")
    List<Groups> getList();
}
