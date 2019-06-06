package com.ayundao.repository;

import com.ayundao.entity.ReleaseSubject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ReleaseSubjectRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/5 16:32
 * @Description: 仓库 - 发布机构
 * @Version: V1.0
 */
@Repository
public interface ReleaseSubjectRepository extends CrudRepository<ReleaseSubject, String> {

}
