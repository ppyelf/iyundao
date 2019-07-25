package com.ayundao.repository;

import com.ayundao.entity.ExamInfoUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ExamInfouserRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/9
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface ExamInfouserRepository extends CrudRepository<ExamInfoUser,String>{

    @Query("select a from ExamInfoUser a where a.exam.id = ?1 and a.user.id = ?2")
    List<ExamInfoUser> findByExamUserId(String examid, String userid);
}
