package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MedicalRange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: MedicalRangeRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 15:18
 * @Description: 仓库 - 医德范围
 * @Version: V1.0
 */
@Repository
public interface MedicalRangeRepository extends BaseRepository<MedicalRange, String> {

    //根据医德档案ID获取医德范围列表
    @Query("select mr from MedicalRange mr where mr.medical.id = (?1)")
    List<MedicalRange> findByMedicalId(String id);
}
