package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: SubjectRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 16:31
 * @Description: 仓库 - 机构
 * @Version: V1.0
 */
@Repository
public interface SubjectRepository extends BaseRepository<Subject, String> {

    //根据code查询实体
    @Query("select s from Subject s where s.code = (?1)")
    Subject findByCode(String code);

    /**
     * 获取个人机构列表
     * @param userId
     * @return
     */
    @Query(value = "select ts.* from t_subject ts left join t_user_relations tur on ts.ID = tur.SUBJECTID where tur.USERID = ?1 group by ts.ID",nativeQuery = true)
    List<Subject> findMySubjectByUserId(String userId);

    /**
     * 根据名字获取实体
     * @param val
     * @return
     */
    @Query("select s from Subject s where s.name = ?1")
    Subject findByName(String val);
}
