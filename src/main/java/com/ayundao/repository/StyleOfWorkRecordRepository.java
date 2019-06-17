package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.StyleOfWorkRecord;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 行风效能扣分记录
 * @Date: 2019/6/15 13:48
 */
public interface StyleOfWorkRecordRepository extends BaseRepository<StyleOfWorkRecord,String> {

    /**
     * 根据行风效能id查询扣分明细
     * @param id
     * @return
     */
    @Query("select s from StyleOfWorkRecord s where s.styleOfWork.id = ?1")
    List<StyleOfWorkRecord> findByStyleOfWorkId(String id);
}
