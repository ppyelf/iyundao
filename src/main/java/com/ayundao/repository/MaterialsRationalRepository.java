package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MaterialsRational;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 耗材点评
 * @Date: 2019/6/15 11:17
 */
@Repository
public interface MaterialsRationalRepository extends BaseRepository<MaterialsRational,String> {

    /**
     * 根据耗材id查询点评
     * @param materialsId
     * @return
     */
    @Query("select m from MaterialsRational m where m.materials.id = ?1")
    List<MaterialsRational> findByMaterialsId(String materialsId);
}
