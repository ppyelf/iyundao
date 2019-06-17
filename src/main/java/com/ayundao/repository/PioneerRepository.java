package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Pioneer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 先锋人物
 * @Date: 2019/6/12 16:23
 */
@Repository
public interface PioneerRepository extends BaseRepository<Pioneer,String> {

    /**
     * 根据部门id查询部门下信息
     * @param list
     * @param pageable
     * @return
     */
    @Query("select e from Pioneer e where e.user.id in (?1) order by e.score desc")
    Page<Pioneer> selectByDepartId(List<String> list, Pageable pageable);

    /**
     * 分页查询所有
     * @param pageable
     * @return
     */
    @Query("select d from Pioneer d")
    Page<Pioneer> selectAll(Pageable pageable);

    /**
     * 根据类别id查询先锋人物
     * @param id
     * @param pageable
     * @return
     */
    @Query("select e from Pioneer e where e.type.id = ?1")
    Page<Pioneer> selectByTypeId(String id,Pageable pageable);

}
