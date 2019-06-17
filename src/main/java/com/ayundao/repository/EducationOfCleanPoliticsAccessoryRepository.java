package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.EducationOfCleanPoliticsAccessory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 廉政教育附件
 * @Date: 2019/6/15 14:14
 */
@Repository
public interface EducationOfCleanPoliticsAccessoryRepository extends BaseRepository<EducationOfCleanPoliticsAccessory,String> {

    /**
     * 根据廉政教育id查看附件
     * @param id
     * @return
     */
    @Query("select e from EducationOfCleanPoliticsAccessory e where e.education.id = ?1")
    List<EducationOfCleanPoliticsAccessory> findByEducationId(String id);

}
