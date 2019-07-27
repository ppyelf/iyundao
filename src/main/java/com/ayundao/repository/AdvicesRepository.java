package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Advices;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AdvicesRepository
 * @project: ayundao
 * @author: 13620
 * @Date: 2019/7/4
 * @Description: 实现 - 活动
 * @Version: V1.0
 */
@Repository
public interface AdvicesRepository extends BaseRepository<Advices,String> {

    @Query("select a from Advices a")
    List<Advices> findAllForList();

    @Query("select a FROM Advices a WHERE a.id in " +
            "(select b.advices.id FROM AdvicesInfoDepar b WHERE b.subject.id = ?1 \n" +
            "OR b.depart.id = ?1 \n" +
            "OR b.groups.id = ?1)")
    List<Advices> findAdvicesByDeptionId(String id);

    @Modifying
    @Query(value = "update t_advices set ADVICESSTATUS = ?2 where id=?1",nativeQuery = true)
    void updatestate(String id, String state);
}
