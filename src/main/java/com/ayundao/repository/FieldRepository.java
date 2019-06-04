package com.ayundao.repository;

import com.ayundao.entity.Field;
import org.hibernate.validator.constraints.URL;
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

    //获取字段所有集合 -- list
    @Query("select f from Field f")
    List<Field> findAllForList();

    //根据ID获取实体信息
    @Query("select f from Field f where f.id = ?1")
    Field findByFieldId(String id);
}
