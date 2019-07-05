package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.AssessmentFraction;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: AssessmentFileRepository
 * @project: ayundao
 * @author: king
 * @Date: 2019/6/7 21:00
 * @Description: 仓库 - 考核分数
 * @Version: V1.0
 */
@Repository
public interface AssessmentFractionRepository extends BaseRepository<AssessmentFraction,String> {
}
