package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.PrescriptionFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: PrescriptionFileRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 4:45
 * @Description: 仓库 - 处方文件
 * @Version: V1.0
 */
@Repository
public interface PrescriptionFileRepository extends BaseRepository<PrescriptionFile, String> {

    //根据处方ID获取附件集合
    @Query("select mf from PrescriptionFile mf where mf.prescription.id = (?1)")
    List<PrescriptionFile> findByPrescriptionId(String id);
}
