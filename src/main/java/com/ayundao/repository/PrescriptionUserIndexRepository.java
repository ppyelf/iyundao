package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.PrescriptionUserIndex;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: PrescriptionUserIndexRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 4:46
 * @Description: 仓库 - 用户指标
 * @Version: V1.0
 */
@Repository
public interface PrescriptionUserIndexRepository extends BaseRepository<PrescriptionUserIndex, String> {

    //根据处方ID获取实体集合
    @Query("select pui from PrescriptionUserIndex pui where pui.prescription.id = (?1)")
    List<PrescriptionUserIndex> findByPrescriptionId(String id);
}
