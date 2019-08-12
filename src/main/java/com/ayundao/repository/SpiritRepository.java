package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.Spirit;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: SpiritRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 5:05
 * @Description: 仓库 - 党内精神
 * @Version: V1.0
 */
@Repository
public interface SpiritRepository extends BaseRepository<Spirit, String> {

    @Modifying
    @Query(value = "UPDATE t_spirit set state = ?2 WHERE id = ?1",nativeQuery = true)
    void updateState(String soiritid, int type);
}
