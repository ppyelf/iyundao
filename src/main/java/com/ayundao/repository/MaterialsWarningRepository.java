package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.MaterialsWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 耗材预警
 * @Date: 2019/6/12 8:58
 */
@Repository
public interface MaterialsWarningRepository extends BaseRepository<MaterialsWarning, String> {

    /**
     * 根据部门id查询部门下信息
     * @param list
     * @param pageable
     * @return
     */
    @Query("select e from MaterialsWarning e where e.user.id in (?1) order by e.score desc")
    Page<MaterialsWarning> selectByDepart(List<String> list, Pageable pageable);

    /**
     * 分页查询所有
     * @param pageable
     * @return
     */
    @Query("select d from Recipe d")
    Page<MaterialsWarning> selectAll(Pageable pageable);
}
