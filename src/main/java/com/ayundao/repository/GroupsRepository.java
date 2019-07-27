package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Groups;
import org.springframework.data.jpa.repository.Query;
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
public interface GroupsRepository extends BaseRepository<Groups, String> {

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

    @Query("select g from Groups g where g.subject is null")
    List<Groups> findSubjectIsNull();

    //获取子集
    @Query("select g from Groups g where g.father.id = (?1)")
    List<Groups> findByFatherId(String id);

    //获取没有父级的集合
    @Query("select g from Groups g where g.father is null")
    List<Groups> getListByFatherIsNull();

    //根据subjectId获取所有父级实体集合
    @Query("select g from Groups g where g.subject.id = ?1 and g.father is null")
    List<Groups> findBySubjectIdAndFatherIsNull(String subjectId);

    //根据CODE查询实体
    @Query("select g from Groups g where g.code = ?1")
    Groups findByCode(String code);
}
