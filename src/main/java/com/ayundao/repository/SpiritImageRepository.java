package com.ayundao.repository;

import com.ayundao.base.BaseRepository;
import com.ayundao.entity.SpiritFile;
import com.ayundao.entity.SpiritImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: SpiritImageRepository
 * @project: ayundao
 * @author: 念
 * @Date: 2019/6/27 5:42
 * @Description: 仓库 - 党内精神图片
 * @Version: V1.0
 */
@Repository
public interface SpiritImageRepository extends BaseRepository<SpiritImage, String> {

    //根据党内精神ID查询实体集合
    @Query("select si from SpiritImage si where si.spirit.id = (?1)")
    List<SpiritImage> findBySpiritId(String id);
}
