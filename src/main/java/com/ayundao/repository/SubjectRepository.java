package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
