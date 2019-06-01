package com.ayundao.repository;

import com.ayundao.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserRelationRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/20 15:08
 * @Description: 仓库 - 用户关系
 * @Version: V1.0
 */
@Repository
public interface UserRelationRepository extends CrudRepository<UserRelation, String> {

    List<UserRelation> findByUser(User user);

    List<UserRelation> findByUserId(String id);

    @Query("select ur from UserRelation ur where ur.user.id = ?1 and ur.subject.id = ?2")
    UserRelation findByUserIdAndSubject(String userId, String subjectId);

    @Query("select ur from UserRelation ur")
    List<UserRelation> getAll();

    @Query("select ur from UserRelation ur where ur.subject.id = ?1 and (ur.depart.id = ?2 or ur.groups.id - ?3)")
    List<UserRelation> findBySubjectAndDepartOrGroups(String subjectId, String departId, String groupsId);
}