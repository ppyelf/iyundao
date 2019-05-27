package com.ayundao.repository;

import com.ayundao.entity.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: MenuRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/23 16:39
 * @Description: 仓库 - 菜单关系
 * @Version: V1.0
 */
@Repository
public interface MenuRepository extends CrudRepository<Menu, String> {

}
