package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.ExamineText;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ExamineTextRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/2 10:52
 * @Description: 审批文本
 * @Version: V1.0
 */
@Repository
public interface ExamineTextRepository extends BaseRepository<ExamineText, String> {

}
