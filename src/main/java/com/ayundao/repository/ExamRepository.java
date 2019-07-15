package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Exam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ExamRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/6
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface ExamRepository  extends BaseRepository<Exam, String> {


}
