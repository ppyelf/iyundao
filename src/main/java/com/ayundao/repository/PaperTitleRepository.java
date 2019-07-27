package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.PaperTitle;
import com.ayundao.entity.Testpaper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: PaperTitleRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/8
 * @Description: 实现 - 试卷题目
 * @Version: V1.0
 */
@Repository
public interface PaperTitleRepository extends BaseRepository<PaperTitle, String> {

    @Query("select a from PaperTitle a where a.testpaper = ?1")
    List<PaperTitle> findByTestpaper(Testpaper testpaper);
}
