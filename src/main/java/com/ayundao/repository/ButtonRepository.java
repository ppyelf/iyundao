package com.ayundao.repository;

import com.ayundao.entity.Button;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ButtonRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/4 15:01
 * @Description: 仓库 - 按钮
 * @Version: V1.0
 */
@Repository
public interface ButtonRepository extends CrudRepository<Button, String> {

    //按钮列表 - list
    @Query("select button from Button button")
    List<Button> findAllList();

    //根据ID获取实体信息
    @Query("select b from Button b where b.id = ?1")
    Button findByButtonId(String id);
}
