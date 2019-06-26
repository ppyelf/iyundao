package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MedicalRange;
import org.springframework.stereotype.Repository;

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

}
