package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MedicalFile;
import org.springframework.stereotype.Repository;

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

}
