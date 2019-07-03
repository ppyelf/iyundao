package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Examine;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ExamineRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/1 9:03
 * @Description: 仓库 - 审批
 * @Version: V1.0
 */
@Repository
public interface ExamineRepository extends BaseRepository<Examine, String> {

}
