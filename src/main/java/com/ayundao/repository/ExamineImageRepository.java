package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.ExamineImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ExamineImageRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/1 10:44
 * @Description: 审核图片
 * @Version: V1.0
 */
@Repository
public interface ExamineImageRepository extends BaseRepository<ExamineImage, String> {

    //根据审核ID查询实体集合
    @Query("select ei from ExamineImage ei where ei.examine.id = ?1")
    List<ExamineImage> findByExamineId(String id);
}
