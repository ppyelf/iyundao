package com.ayundao.repository;

import com.ayundao.entity.AdvicesInfoDepar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AdvicesInfoDeparRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/5
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface AdvicesInfoDeparRepository extends CrudRepository<AdvicesInfoDepar, String>{



}
