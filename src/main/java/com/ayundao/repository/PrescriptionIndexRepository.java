package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.PrescriptionIndex;
import com.ayundao.entity.PrescriptionIndex;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: PrescriptionIndexRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 4:45
 * @Description: 仓库 - 处方指标
 * @Version: V1.0
 */
@Repository
public interface PrescriptionIndexRepository extends BaseRepository<PrescriptionIndex, String> {

    //根据处方ID获取实体集合
    @Query("select mi from PrescriptionIndex mi where mi.prescription.id = (?1)")
    List<PrescriptionIndex> findByPrescriptionId(String id);

    //获取表中CODE的最大值
    @Query(value = "SELECT IFNULL(max(mi.`CODE`),0) as maxNum from t_prescription_index mi", nativeQuery = true)
    int getLastCode();

    //获取父级ID为空的集合
    @Query("select mi from PrescriptionIndex mi where mi.father is null")
    List<PrescriptionIndex> findPrescriptionIndexByFatherIsNullForList();

    //获取指标子集
    @Query("select mi from PrescriptionIndex mi where mi.father.id = (?1)")
    List<PrescriptionIndex> findPrescriptionIndexChild(String id);
}
