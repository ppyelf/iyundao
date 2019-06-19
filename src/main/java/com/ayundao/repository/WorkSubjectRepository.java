package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.WorkSubject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: WorkSubjectRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/18 17:57
 * @Description: 仓库 - 工作部门
 * @Version: V1.0
 */
@Repository
public interface WorkSubjectRepository extends BaseRepository<WorkSubject, String> {

    //根据工作ID查找实体内容
    @Query("select ws from WorkSubject ws where ws.work.id = (?1)")
    WorkSubject findByWorkId(String id);
}
