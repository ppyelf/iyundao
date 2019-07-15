package com.ayundao.repository;

import com.ayundao.entity.PaperAnswer;
import com.ayundao.entity.PaperTitle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: PaperAnswerRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface PaperAnswerRepository extends CrudRepository<PaperAnswer ,String>{

    @Query("select a from PaperAnswer a where a.paperTitle = ?1")
    List<PaperAnswer> findByPaperTitles(PaperTitle paperTitle);
}
