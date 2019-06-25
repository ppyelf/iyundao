package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MedicalIndex;
import org.springframework.stereotype.Repository;

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

}
