package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.PioneerIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 先锋指数
 * @Date: 2019/6/12 16:23
 */
@Repository
public interface PioneerIndexRepository extends BaseRepository<PioneerIndex,String> {

    /**
     * 根据部门id查询部门下信息
     * @param list
     * @param pageable
     * @return
     */
    @Query("select e from PioneerIndex e where e.userId.id in (?1) order by e.score desc")
    Page<PioneerIndex> selectByDepartId(List<String> list, Pageable pageable);

    /**
     * 分页查询所有
     * @param pageable
     * @return
     */
    @Query("select d from PioneerIndex d")
    Page<PioneerIndex> selectAll(Pageable pageable);

}
