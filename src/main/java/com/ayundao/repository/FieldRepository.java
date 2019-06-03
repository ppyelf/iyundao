package com.ayundao.repository;

import com.ayundao.entity.Field;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


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

    //根据页面ID获取字段集合信息
    @Query("select f from Field f where f.page.id = ?1")
    List<Field> findByPageId(String pageId);
}
