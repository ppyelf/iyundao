package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MedicalIndex;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: MedicalIndexRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 15:18
 * @Description: 仓库 - 医德指标
 * @Version: V1.0
 */
@Repository
public interface MedicalIndexRepository extends BaseRepository<MedicalIndex, String> {

    //根据档案ID获取实体集合
    @Query("select mi from MedicalIndex mi where mi.medical.id = (?1)")
    List<MedicalIndex> findByMedicalId(String id);

    //获取表中CODE的最大值
    @Query(value = "SELECT IFNULL(max(mi.`CODE`),0) as maxNum from t_medical_index mi", nativeQuery = true)
    int getLastCode();

    //获取父级ID为空的集合
    @Query("select mi from MedicalIndex mi where mi.father is null")
    List<MedicalIndex> findMedicalIndexByFatherIsNullForList();

    //获取指标子集
    @Query("select mi from MedicalIndex mi where mi.father.id = (?1)")
    List<MedicalIndex> findMedicalIndexChild(String id);
}
