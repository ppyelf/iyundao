package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.EthicsRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ink-feather
 * @Description: 医德档案持久层
 * @Date: 2019/6/12 8:47
 */
@Repository
public interface EthicsRecordRepository extends BaseRepository<EthicsRecord, String> {


    /**
     * 根据部门id查询部门下信息
     * @param list
     * @param pageable
     * @return
     */
    @Query("select e from EthicsRecord e where e.user.id in (?1) order by e.score desc")
    Page<EthicsRecord> selectByDepart(List<String> list, Pageable pageable);

    /**
     * 分页查询所有
     * @param pageable
     * @return
     */
    @Query("select d from Recipe d")
    Page<EthicsRecord> selectAll(Pageable pageable);
}
