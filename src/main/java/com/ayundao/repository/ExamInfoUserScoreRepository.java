package com.ayundao.repository;

import com.ayundao.entity.Exam;
import com.ayundao.entity.ExamInfoUserScore;
import com.ayundao.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ExamInfoUserScoreRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface ExamInfoUserScoreRepository extends CrudRepository<ExamInfoUserScore, String>{

    @Query("select a from ExamInfoUserScore a where a.exam = ?1")
    List<ExamInfoUserScore> findAllByExamId(Exam exam);

    @Query("select a from ExamInfoUserScore a where a.exam.id = ?1 and a.user.id = ?2")
    ExamInfoUserScore findByExamUserId(String examid, String userid);

    @Query("select a from ExamInfoUserScore a where a.user = ?1")
    List<ExamInfoUserScore> findbyUser(User user);
}
