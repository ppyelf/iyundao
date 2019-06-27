package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.PrescriptionRange;
import com.ayundao.entity.PrescriptionRange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: PrescriptionRangeRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 4:44
 * @Description: 仓库 - 处方范围
 * @Version: V1.0
 */
@Repository
public interface PrescriptionRangeRepository extends BaseRepository<PrescriptionRange, String> {

    //根据医德档案ID获取医德范围列表
    @Query("select pr from PrescriptionRange pr where pr.prescription.id = (?1)")
    List<PrescriptionRange> findByPrescriptionId(String id);
}
