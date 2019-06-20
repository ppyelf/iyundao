package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Work;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: WorkRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 17:31
 * @Description: 仓库 - 中心工作
 * @Version: V1.0
 */
@Repository
public interface WorkRepository extends BaseRepository<Work, String> {

}
