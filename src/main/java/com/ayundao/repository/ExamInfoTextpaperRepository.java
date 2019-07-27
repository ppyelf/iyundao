package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.ExamInfoTextpaper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ExamInfoTextpaperRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/7
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface ExamInfoTextpaperRepository extends BaseRepository<ExamInfoTextpaper, String> {

    @Query("select a from ExamInfoTextpaper a where a.exam.id = ?1")
    List<ExamInfoTextpaper> findByExamId(String id);
}
