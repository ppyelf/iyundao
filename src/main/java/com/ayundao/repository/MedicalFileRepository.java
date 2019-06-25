package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MedicalFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: MedicalFileRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 15:17
 * @Description: 仓库 - 医德附件
 * @Version: V1.0
 */
@Repository
public interface MedicalFileRepository extends BaseRepository<MedicalFile, String> {

    //根据医德ID获取附件集合
    @Query("select mf from MedicalFile mf where mf.medical.id = (?1)")
    List<MedicalFile> findByMedicalId(String id);
}
