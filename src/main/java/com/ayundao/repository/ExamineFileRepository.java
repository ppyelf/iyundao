package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.ExamineFile;
import com.ayundao.entity.ExamineImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ExamineFileRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/7/1 10:39
 * @Description: 仓库 - 审核附件
 * @Version: V1.0
 */
@Repository
public interface ExamineFileRepository extends BaseRepository<ExamineFile, String> {

    //根据审核ID查询实体集合
    @Query("select ef from ExamineFile ef where ef.examine.id = ?1")
    List<ExamineFile> findByExamineId(String id);
}
