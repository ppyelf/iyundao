package com.ayundao.repository;

import com.ayundao.entity.Field;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



/**
 * @ClassName: FieldRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/24 2:41
 * @Description: 仓库 - 字段
 * @Version: V1.0
 */
@Repository
public interface FieldRepository extends CrudRepository<Field, String> {

}
