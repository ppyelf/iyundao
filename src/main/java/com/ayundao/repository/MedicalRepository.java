package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Medical;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: MedicalRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/24 15:16
 * @Description: 仓库 - 医德
 * @Version: V1.0
 */
@Repository
public interface MedicalRepository extends BaseRepository<Medical, String> {

}
