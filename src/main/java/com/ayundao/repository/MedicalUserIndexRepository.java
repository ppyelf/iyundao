package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MedicalRange;
import com.ayundao.entity.MedicalUserIndex;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: MedicalUserIndexRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 15:19
 * @Description: 仓库 - 医德用户指标
 * @Version: V1.0
 */
@Repository
public interface MedicalUserIndexRepository extends BaseRepository<MedicalUserIndex, String> {

    //根据档案ID获取实体集合
    @Query("select mui from MedicalUserIndex mui where mui.medical.id = (?1)")
    List<MedicalUserIndex> findByMedicalId(String id);
}
