package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.PioneerType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 先锋人物类别
 * @Date: 2019/6/15 14:44
 */
@Repository
public interface PioneerTypeRepository extends BaseRepository<PioneerType,String> {

    List<PioneerType> findByDepartId(String id);

    List<PioneerType> findByGroupId(String id);

    List<PioneerType> findBySubjectId(String id);
}
