package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Prescription;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: PrescriptionRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/26 4:42
 * @Description: 仓库 - 处方
 * @Version: V1.0
 */
@Repository
public interface PrescriptionRepository extends BaseRepository<Prescription, String> {

}
