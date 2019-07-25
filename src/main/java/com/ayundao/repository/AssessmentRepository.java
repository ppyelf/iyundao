package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Assessment;
import com.ayundao.entity.AssessmentRange;
import com.ayundao.entity.PioneerIndex;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName: AssessmentRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/10
 * @Description: 实现 - 活动
 * @Version: V1.0
 */

@Repository
public interface AssessmentRepository extends BaseRepository<Assessment, String> {


}
